package com.example.thejokes;

import java.util.Random;

public class TheJokes {

    private static String[] mJokes = {
            "\"Hello I.T.\"\n\"Have you tried turning it off and on again?\"",
            "There's a band called 1023MB. They haven't had any gigs yet.",
            "If you put a million monkeys on a million keyboards, one of them will eventually write a Java program. " +
                    "The rest of them will write Perl programs.",
            "\"Knock, knock. Who's there?\" very long pause...\n\"Java.\"",
            "Why did the developer go broke?\nBecause he used up all his cache",
            "Why do Java developers wear glasses?\nBecause they can't C#",
            "Why is it that programmers always confuse Halloween with Christmas?\nBecause 31 OCT = 25 DEC.",
            "How many programmers does it take to change a light bulb?\nNone. It's a hardware problem.",
            "There are 10 types of people in the world: those who understand binary, and those who don't."
    };

    public TheJokes() {
        // empty constructor
    }

    public static String getJoke() {
        return mJokes[new Random().nextInt(mJokes.length)];
    }
}
