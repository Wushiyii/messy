package com.wushiyii.messy.spi;

import com.wushiyii.messy.grammar.spi.Animal;
import org.junit.Test;
import java.util.ServiceLoader;

public class SpiTest {
    @Test
    public void sayHello() {
        ServiceLoader<Animal> serviceLoader = ServiceLoader.load(Animal.class);
        serviceLoader.forEach(Animal::sayHello);
    }
}
