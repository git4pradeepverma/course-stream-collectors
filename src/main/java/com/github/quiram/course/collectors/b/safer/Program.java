package com.github.quiram.course.collectors.b.safer;

import com.github.quiram.course.collectors.a.safer.commands.Command;
import com.github.quiram.course.collectors.a.safer.commands.PingCommand;
import com.github.quiram.course.collectors.a.safer.commands.RepeatCommand;
import com.github.quiram.course.collectors.a.safer.commands.ReverseCommand;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;

import static com.github.quiram.course.collectors.b.safer.collector.Collectors.toMaybeOne;
import static java.util.Arrays.asList;

public class Program {
    private static final List<Command> commands = asList(new PingCommand(), new RepeatCommand(), new ReverseCommand());

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            final String input = scanner.nextLine();

            try {
                final Optional<Command> maybeCommand = commands.stream()
                        .filter(command -> command.supports(input))
                        .collect(toMaybeOne());

                maybeCommand.ifPresentOrElse(
                        command -> System.out.println(command.apply(input)),
                        () -> System.err.printf("No command supports input '%s'%n", input)
                );
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        scanner.close();
    }

    private static Collector<Command, ?, Optional<Command>> toMaybeOneCommand() {
        return toMaybeOne("More than command supports this input, options are: ");
    }
}
