package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public interface Executable {
    Response execute(Request request);
}
