package ru.nsu.android.drinkwithme.modules.database.parameters;

public interface IParametersDBHandler {
    void setName(String name);
    String getName();
    void setWeight(int weight);
    int getWeight();
    void setHeight(int height);
    int getHeight();
    void setGender(String gender);
    String getGender();
}
