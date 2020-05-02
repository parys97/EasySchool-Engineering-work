package com.example.easyschool.domain;

public class Views {
    public interface Id {}

    public interface IdName extends Id {}

    public interface FullProject extends IdName {}

    public interface FullUser extends IdName {}

    public interface FullFile extends IdName {}
}
