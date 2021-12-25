package com.guide.common.demo.generator.v2;

public interface IdGenerator
{
    String generate() throws IdGenerationFailureException;
}
