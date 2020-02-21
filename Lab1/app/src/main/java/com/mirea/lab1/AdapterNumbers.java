package com.mirea.lab1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterNumbers extends RecyclerView.Adapter<AdapterNumbers.ViewHolder> {

    private List<Integer> list = new ArrayList<>();

    public void setList() {
        for (int i = 1; i <= 1_000_000; ++i) {
            list.add(i);
        }
    }

    @NonNull
    @Override
    public AdapterNumbers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_numbers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNumbers.ViewHolder holder, int position) {
        String number = converter(list.get(position));

        holder.text.setText(number);
        holder.layout.setBackgroundColor((position % 2 == 1) ? Color.LTGRAY : Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;
        private final LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.textInItemOfList);
            layout = itemView.findViewById(R.id.item_list_layout);
        }
    }

    private String converter(int number) {
        if (number < 100) {
            return lessHundred(number, true);
        }
        if (number < 1000) {
            return lessThousand(number, true);
        }
        if (number < 100_000) {
            return lessOneHundredThousand(number, true).replaceAll("\\s+", " ");
        }
        if (number < 1_000_000) {
            return lessMillion(number).replaceAll("\\s+", " ");
        }
        return "Один миллион";
    }

    private String lessHundred(int number, boolean toUpper) {
        String result;

        String[] units = {"", "один", "два", "три", "четыре", "пять",
                "шесть", "семь", "восемь", "девять", "десять",
                "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать",
                "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};

        String[] dozens = {"двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят",
                "семьдесят", "восемьдесят", "девяносто", "сто"};

        if (number < 20) {
            return toUpper ? units[number].substring(0, 1).toUpperCase() + units[number].substring(1)
                    : units[number];
        }

        int fstNumeral = number / 10 - 2;
        int sndNumeral = number % 10;

        result = toUpper ? dozens[fstNumeral].substring(0, 1).toUpperCase() + dozens[fstNumeral].substring(1)
                : dozens[fstNumeral];

        return result + " " + units[sndNumeral];
    }

    private String lessThousand(int number, boolean toUpper) {
        String result;

        String[] hundreds = {"сто", "двести", "триста", "четыреста", "пятьсот",
                "шестьсот", "семьсот","восемьсот", "девятьсот"};

        int fstNumeral = number / 100 - 1;
        result = toUpper ? hundreds[fstNumeral].substring(0, 1).toUpperCase() + hundreds[fstNumeral].substring(1)
                : hundreds[fstNumeral];

        result += " " + lessHundred(number % 100, false);

        return result;
    }

    private String lessOneHundredThousand(int number, boolean toUpper) {
        String result = "";

        String[] thousands = {"одна тысяча", "две", "тысячи", "тысяч"};
        int lastThreeNumerals = number % 1000;
        int fstNumerals = number / 1000;

        if (fstNumerals < 10) {
            switch (fstNumerals) {
                case 1:
                    result = toUpper ? thousands[0].substring(0, 1).toUpperCase() + thousands[0].substring(1)
                            : thousands[0];
                    break;
                case 2:
                    result = toUpper ? thousands[1].substring(0, 1).toUpperCase() + thousands[1].substring(1)
                            : thousands[1];
                    result += " " + thousands[2];
                    break;
                case 3:
                case 4:
                    result = lessHundred(fstNumerals, toUpper) + " " + thousands[2];
                    break;
                default:
                    result = lessHundred(fstNumerals, toUpper) + " " + thousands[3];
            }
            if (lastThreeNumerals < 100) {
                result += " " + lessHundred(lastThreeNumerals, false);
                return result;
            }
            result += " " + lessThousand(lastThreeNumerals, false);
            return result;
        }

        int sndNumeral = fstNumerals % 10;

        if (fstNumerals < 20) {
            result = lessHundred(fstNumerals, true) + " " + thousands[3] + " ";
            result += (lastThreeNumerals < 100) ? lessHundred(lastThreeNumerals, false) : lessThousand(lastThreeNumerals, false);
            return result;
        }
        if (sndNumeral == 1) {
            result = lessHundred(fstNumerals - 1, toUpper) + " " + thousands[0];
        } else if (sndNumeral >= 2 && sndNumeral <= 4) {
            switch (sndNumeral) {
                case 2:
                    result = lessHundred(fstNumerals - 2, toUpper);
                    result += " " + thousands[1] + thousands[2];
                    break;
                case 3:
                case 4:
                    result = lessHundred(fstNumerals, toUpper) + " " + thousands[2];
            }
        } else {
            result = lessHundred(fstNumerals, toUpper) + " " + thousands[3];
        }

        result += (lastThreeNumerals < 100) ? " " + lessHundred(lastThreeNumerals, false)
                : " " + lessThousand(lastThreeNumerals, false);
        return result;
    }

    private String lessMillion(int number) {
        String result;
        String[] thousands = {"одна тысяча", "две", "тысячи", "тысяч"};
        int threeFstNumerals = number / 1000;
        int lastNumerals = number % 1000;

        if (threeFstNumerals % 100 == 0) { //100.000 200.000
            result = lessThousand(threeFstNumerals, true) + " " + thousands[3];
            if (lastNumerals < 100) {
                result += " " + lessHundred(lastNumerals, false);
            } else {
                result += " " + lessThousand(lastNumerals, false);
            }
            return result;
        }

        int twoNumerals = threeFstNumerals % 100;

        result = lessThousand(threeFstNumerals - twoNumerals, true) + " ";
        result += lessOneHundredThousand(twoNumerals * 1000, false);
        result += (lastNumerals < 100) ? lessHundred(lastNumerals, false) : lessThousand(lastNumerals,false);
        return result;
    }
}
