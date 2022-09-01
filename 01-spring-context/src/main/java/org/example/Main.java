package org.example;

import org.example.animals.Cat;
import org.example.animals.Dog;
import org.example.animals.Fish;
import org.example.animals.Parrot;
import org.example.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Parrot p = context.getBean(Parrot.class);
        // Parrot p = (Parrot) context.getBean("parrot"); same result
        System.out.println("The name of the parrot in the context is: " + p.getName());

        // Other beans added to the context
        System.out.println(context.getBean("hello") + " " + context.getBean("ten"));

        // if there are multiple beans of the requested type you get an exception
        try {
            context.getBean(Cat.class);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // you can get one of the beans specifying its bean's name
        Cat c = context.getBean("cat2", Cat.class);
        System.out.println(c.getName());

        // getting a bean defined as a component
        Dog d = context.getBean(Dog.class);
        System.out.println(d.getName());

        // adding beans programmatically
        List<Fish> list = Arrays.asList(
                new Fish("Betta", "Red"),
                new Fish("Guppy", "Blue"),
                new Fish("Goldfish", "Gold")
        );
        int i = 1;
        for (Fish fish : list) {
            if (!fish.getColor().equals("Blue")) {
                context.registerBean("fish" + i++, Fish.class, () -> fish);
            }
        }
        System.out.println(context.getBean("fish2", Fish.class).getName());

        // number of beans in the context
        System.out.println(context.getBeanDefinitionCount());

        // names of the beans in the context, e.g. "projectContext" and "parrot"
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }
}
