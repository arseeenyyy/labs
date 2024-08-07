package arseeenyyy.com.github.common.interfaces;

import arseeenyyy.com.github.common.commands.BaseCommand;

import java.util.Map;

public interface InCommandManager {
    Map<String, BaseCommand> getCommandList();
}
