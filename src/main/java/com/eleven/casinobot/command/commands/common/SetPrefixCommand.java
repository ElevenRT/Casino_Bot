package com.eleven.casinobot.command.commands.common;

import com.eleven.casinobot.command.CommandContext;
import com.eleven.casinobot.command.ICommand;
import com.eleven.casinobot.config.PrefixConfig;
import com.eleven.casinobot.database.DataBaseQuery;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class SetPrefixCommand implements ICommand {

    DataBaseQuery query = new DataBaseQuery();
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final List<String> chat = ctx.getStrings();
        final Member member = ctx.getMember();

        if (!member.hasPermission(Permission.MANAGE_SERVER)) {
            channel.sendMessage("이 명령어를 사용하려면 관리자 권한이 필요합니다!").queue();
            return;
        }

        if (chat.isEmpty()) {
            channel.sendMessage("Prefix를 지정하지 않았습니다!").queue();
            return;
        }

        final String prefix = String.join("",chat);
        query.update(channel.getGuild().getIdLong(),prefix, "GUILD", "prefix", "guild_id");
        channel.sendMessage("변경됨").queue();
    }

    @Override
    public String getName() {
        return "setprefix";
    }

    @Override
    public String getHelp() {
        return "Sets the prefix for this server\n" +
                "Usage: `" + PrefixConfig.PREFIXS + "setprefix <prefix>`";
    }

}
