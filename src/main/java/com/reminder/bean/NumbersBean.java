package com.reminder.bean;

import java.util.List;

public class NumbersBean {

    private String numbersError = "FPFP";

    private List<String> numbers;

    public String getNumbersError() {
        return numbersError;
    }

    public void setNumbersError(String numbersError) {
        this.numbersError = numbersError;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}
