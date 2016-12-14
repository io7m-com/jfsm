/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jfsm.tests.core;

import com.io7m.jfsm.core.FSMEnumMutable;
import com.io7m.jfsm.core.FSMEnumMutableBuilderType;
import com.io7m.jfsm.core.FSMTransitionException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class FSMEnumMutableTest
{
  private enum TrafficLight
  {
    RED,
    YELLOW,
    GREEN
  }

  @Rule public ExpectedException expected = ExpectedException.none();

  @Test
  public void testTransitionInvalid0()
  {
    final FSMEnumMutable<TrafficLight> m = trafficLight();

    Assert.assertEquals(TrafficLight.RED, m.current());
    this.expected.expect(FSMTransitionException.class);
    m.transition(TrafficLight.GREEN);
  }

  @Test
  public void testTransitionInvalid1()
  {
    final FSMEnumMutable<TrafficLight> m = trafficLight();

    Assert.assertEquals(TrafficLight.RED, m.current());
    m.transition(TrafficLight.YELLOW);
    Assert.assertEquals(TrafficLight.YELLOW, m.current());
    m.transition(TrafficLight.GREEN);
    Assert.assertEquals(TrafficLight.GREEN, m.current());
    this.expected.expect(FSMTransitionException.class);
    m.transition(TrafficLight.RED);
  }

  @Test
  public void testTransitionInvalidEmpty()
  {
    final FSMEnumMutableBuilderType<TrafficLight> b =
      FSMEnumMutable.builder(TrafficLight.RED);
    final FSMEnumMutable<TrafficLight> m = b.build();

    Assert.assertEquals(TrafficLight.RED, m.current());
    this.expected.expect(FSMTransitionException.class);
    m.transition(TrafficLight.RED);
  }

  @Test
  public void testTransitionOK()
  {
    final FSMEnumMutable<TrafficLight> m = trafficLight();

    Assert.assertEquals(TrafficLight.RED, m.current());
    m.transition(TrafficLight.YELLOW);
    Assert.assertEquals(TrafficLight.YELLOW, m.current());
    m.transition(TrafficLight.GREEN);
    Assert.assertEquals(TrafficLight.GREEN, m.current());
    m.transition(TrafficLight.YELLOW);
    Assert.assertEquals(TrafficLight.YELLOW, m.current());
    m.transition(TrafficLight.RED);
    Assert.assertEquals(TrafficLight.RED, m.current());
    m.transition(TrafficLight.YELLOW);
    Assert.assertEquals(TrafficLight.YELLOW, m.current());
    m.transition(TrafficLight.GREEN);
    Assert.assertEquals(TrafficLight.GREEN, m.current());
    m.transition(TrafficLight.YELLOW);
    Assert.assertEquals(TrafficLight.YELLOW, m.current());
    m.transition(TrafficLight.RED);
    Assert.assertEquals(TrafficLight.RED, m.current());
    m.transition(TrafficLight.YELLOW);
    Assert.assertEquals(TrafficLight.YELLOW, m.current());
    m.transition(TrafficLight.GREEN);
    Assert.assertEquals(TrafficLight.GREEN, m.current());
  }

  private static FSMEnumMutable<TrafficLight> trafficLight()
  {
    final FSMEnumMutableBuilderType<TrafficLight> b =
      FSMEnumMutable.builder(TrafficLight.RED);
    b.addTransition(TrafficLight.RED, TrafficLight.YELLOW);
    b.addTransition(TrafficLight.YELLOW, TrafficLight.GREEN);
    b.addTransition(TrafficLight.GREEN, TrafficLight.YELLOW);
    b.addTransition(TrafficLight.YELLOW, TrafficLight.RED);
    return b.build();
  }
}
