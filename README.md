jfsm
===

[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.jfsm/com.io7m.jfsm.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.jfsm%22)
[![Maven Central (snapshot)](https://img.shields.io/nexus/s/com.io7m.jfsm/com.io7m.jfsm?server=https%3A%2F%2Fs01.oss.sonatype.org&style=flat-square)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/io7m/jfsm/)
[![Codecov](https://img.shields.io/codecov/c/github/io7m-com/jfsm.svg?style=flat-square)](https://codecov.io/gh/io7m-com/jfsm)

![com.io7m.jfsm](./src/site/resources/jfsm.jpg?raw=true)

| JVM | Platform | Status |
|-----|----------|--------|
| OpenJDK (Temurin) Current | Linux | [![Build (OpenJDK (Temurin) Current, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jfsm/main.linux.temurin.current.yml)](https://www.github.com/io7m-com/jfsm/actions?query=workflow%3Amain.linux.temurin.current)|
| OpenJDK (Temurin) LTS | Linux | [![Build (OpenJDK (Temurin) LTS, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jfsm/main.linux.temurin.lts.yml)](https://www.github.com/io7m-com/jfsm/actions?query=workflow%3Amain.linux.temurin.lts)|
| OpenJDK (Temurin) Current | Windows | [![Build (OpenJDK (Temurin) Current, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jfsm/main.windows.temurin.current.yml)](https://www.github.com/io7m-com/jfsm/actions?query=workflow%3Amain.windows.temurin.current)|
| OpenJDK (Temurin) LTS | Windows | [![Build (OpenJDK (Temurin) LTS, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jfsm/main.windows.temurin.lts.yml)](https://www.github.com/io7m-com/jfsm/actions?query=workflow%3Amain.windows.temurin.lts)|

## jfsm

The `jfsm` package provides implementations of finite state machines.

## Features

* Mutable enum-based finite state machines.
* Written in pure Java 21.
* High coverage test suite.
* [OSGi-ready](https://www.osgi.org/)
* [JPMS-ready](https://en.wikipedia.org/wiki/Java_Platform_Module_System)
* ISC license.

## Usage

Declare a type that will be used to represent the states of the FSM.
An `enum` type or a `sealed` hierarchy is a good choice.

```
enum TrafficLight
{
  RED,
  YELLOW,
  GREEN
}
```

Declare the allowed state transitions and build an FSM:

```
final FSMEnumMutableBuilderType<TrafficLight> b =
  FSMEnumMutable.builder(TrafficLight.RED);
b.addTransition(TrafficLight.RED, TrafficLight.YELLOW);
b.addTransition(TrafficLight.YELLOW, TrafficLight.GREEN);
b.addTransition(TrafficLight.GREEN, TrafficLight.YELLOW);
b.addTransition(TrafficLight.YELLOW, TrafficLight.RED);
var fsm = b.build();
```

Execute transitions:

```
assert TrafficLight.RED == m.current();

m.transition(TrafficLight.YELLOW;
assert TrafficLight.YELLOW == m.current();

m.transition(TrafficLight.GREEN;
assert TrafficLight.GREEN == m.current();

m.transition(TrafficLight.YELLOW;
assert TrafficLight.YELLOW == m.current();

m.transition(TrafficLight.RED;
assert TrafficLight.RED == m.current();

m.transition(TrafficLight.YELLOW;
assert TrafficLight.YELLOW == m.current();

m.transition(TrafficLight.GREEN;
assert TrafficLight.GREEN == m.current();

m.transition(TrafficLight.YELLOW;
assert TrafficLight.YELLOW == m.current();

m.transition(TrafficLight.RED;
assert TrafficLight.RED == m.current();

m.transition(TrafficLight.YELLOW;
assert TrafficLight.YELLOW == m.current();

m.transition(TrafficLight.GREEN;
assert TrafficLight.GREEN == m.current();
```

The FSM will throw an `FSMTransitionException` if an attempt is made to
perform a transition that is not permitted.

