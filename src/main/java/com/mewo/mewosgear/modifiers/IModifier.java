package com.mewo.mewosgear.modifiers;

public interface IModifier {
    String getName();
    int getTier();
    ModifierCategory getCategory();

    void apply();
    void remove();
    void activate();
}
