/*
 * Copyright © 2016 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jfsm.core;

import java.util.Objects;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * A mutable finite state machine.
 *
 * @param <T> The type of states
 */

public final class FSMEnumMutable<T extends Enum<T>>
{
  private final EnumMap<T, EnumSet<T>> map;
  private T current;

  private FSMEnumMutable(
    final EnumMap<T, EnumSet<T>> in_map,
    final T initial)
  {
    this.map = Objects.requireNonNull(in_map, "Map");
    this.current = Objects.requireNonNull(initial, "Initial");
  }

  /**
   * Create a new builder.
   *
   * @param init The initial state
   * @param <T>  The type of states
   *
   * @return A new builder
   */

  public static <T extends Enum<T>> FSMEnumMutableBuilderType<T> builder(
    final T init)
  {
    return new Builder<>(Objects.requireNonNull(init, "Initial"));
  }

  /**
   * @return The current state
   */

  public T current()
  {
    return this.current;
  }

  /**
   * Transition from the current state to the given state.
   *
   * @param state The target state
   *
   * @throws FSMTransitionException If no transition is allowed between the
   *                                states
   */

  public void transition(
    final T state)
    throws FSMTransitionException
  {
    Objects.requireNonNull(state, "state");

    if (this.map.containsKey(this.current)) {
      final EnumSet<T> nexts = this.map.get(this.current);
      if (nexts.contains(state)) {
        this.current = state;
        return;
      }
    }

    final String separator = System.lineSeparator();
    final StringBuilder sb = new StringBuilder(128);
    sb.append("Cannot transition between the given states.");
    sb.append(separator);
    sb.append("  Current state:   ");
    sb.append(this.current);
    sb.append(separator);
    sb.append("  Requested state: ");
    sb.append(state);
    sb.append(separator);
    throw new FSMTransitionException(sb.toString());
  }

  private static final class Builder<T extends Enum<T>> implements
    FSMEnumMutableBuilderType<T>
  {
    private final EnumMap<T, EnumSet<T>> map;
    private final T initial;

    Builder(
      final T in_initial)
    {
      this.map = new EnumMap<>(in_initial.getDeclaringClass());
      this.initial = Objects.requireNonNull(in_initial, "Initial");
    }

    @Override
    public FSMEnumMutableBuilderType<T> addTransition(
      final T from,
      final T to)
    {
      Objects.requireNonNull(from, "From");
      Objects.requireNonNull(to, "To");

      final EnumSet<T> set_to;
      if (this.map.containsKey(from)) {
        set_to = this.map.get(from);
        set_to.add(to);
      } else {
        set_to = EnumSet.of(to);
      }

      this.map.put(from, set_to);
      return this;
    }

    @Override
    public FSMEnumMutable<T> build()
    {
      return new FSMEnumMutable<>(new EnumMap<>(this.map), this.initial);
    }
  }
}
