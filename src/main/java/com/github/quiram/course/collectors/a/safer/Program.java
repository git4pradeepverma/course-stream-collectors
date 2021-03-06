package com.github.quiram.course.collectors.a.safer;

import com.github.quiram.course.collectors.a.safer.commands.Command;
import com.github.quiram.course.collectors.a.safer.commands.PingCommand;
import com.github.quiram.course.collectors.a.safer.commands.RepeatCommand;
import com.github.quiram.course.collectors.a.safer.commands.ReverseCommand;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.util.Arrays.asList;

public class Program {
    private static final List<Command> commands = asList(new PingCommand(), new RepeatCommand(), new ReverseCommand());

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            final String input = scanner.nextLine();

            final Optional<Command> maybeCommand = commands.stream()
                    .filter(command -> command.supports(input))
                    .findFirst();

            maybeCommand.ifPresentOrElse(
                    command -> System.out.println(command.apply(input)),
                    () -> System.err.printf("No command supports input '%s'%n", input)
            );
        }

        scanner.close();
    }
}
