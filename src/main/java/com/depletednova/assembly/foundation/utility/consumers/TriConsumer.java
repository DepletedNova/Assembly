package com.depletednova.assembly.foundation.utility.consumers;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
	void accept(A a, B b, C c);
}
