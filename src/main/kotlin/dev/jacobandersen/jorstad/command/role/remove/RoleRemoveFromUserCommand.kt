package dev.jacobandersen.jorstad.command.role.remove

import cloud.commandframework.Command
import cloud.commandframework.arguments.StaticArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.javacord.sender.JavacordCommandSender
import dev.jacobandersen.jorstad.JorstadBot
import dev.jacobandersen.jorstad.command.api.TerminalSubcommand
import dev.jacobandersen.jorstad.ext.resolveGuildFromContext
import dev.jacobandersen.jorstad.ext.resolveDiscordRoleFromArgument
import dev.jacobandersen.jorstad.ext.resolveDiscordUserFromArgument

class RoleRemoveFromUserCommand(private val bot: JorstadBot) : TerminalSubcommand {
    override fun terminal(builder: Command.Builder<JavacordCommandSender>): Command.Builder<JavacordCommandSender> {
        return builder
            .argument(StaticArgument.of("user"))
            .argument(StringArgument.of("user"))
            .argument(StringArgument.of("role"))
            .handler { handler ->
                val guild = bot.discord.api.resolveGuildFromContext(handler) ?: return@handler
                val user = handler.resolveDiscordUserFromArgument(guild) ?: return@handler
                val role = handler.resolveDiscordRoleFromArgument(guild) ?: return@handler

                user.removeRole(role, "Removing role as requested by privileged user")

                handler.sender.sendSuccessMessage("Removed user from the specified role.")
            }
    }
}
