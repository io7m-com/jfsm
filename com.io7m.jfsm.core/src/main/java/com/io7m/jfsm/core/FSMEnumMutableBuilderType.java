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

/**
 * The type of builders for mutable enum-based finite state machines.
 *
 * @param <T> The type of state values
 */

public interface FSMEnumMutableBuilderType<T extends Enum<T>>
{
  /**
   * Add a transition from state {@code from} to state {@code to}.
   *
   * @param from The starting state
   * @param to   The target state
   *
   * @return This builder
   */

  FSMEnumMutableBuilderType<T> addTransition(
    T from,
    T to);

  /**
   * @return A new finite state machine based on all of the parameters given so
   * far
   */

  FSMEnumMutable<T> build();
}
