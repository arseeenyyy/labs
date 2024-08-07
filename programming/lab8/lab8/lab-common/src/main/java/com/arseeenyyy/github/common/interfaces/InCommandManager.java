package com.arseeenyyy.github.common.interfaces;

import com.arseeenyyy.github.common.commands.BaseCommand;

import java.util.Map;

public interface InCommandManager {
    Map<String, BaseCommand> getCommandList();
}
