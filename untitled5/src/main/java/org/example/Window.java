package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame {
    private int windowWidth = 1000;
    private int WindowHeight = 600;
    private int buttonWidth = 100;
    private int buttonHeight = 50;
    private int chooseWidth = 3 * buttonWidth + 200;

    private int chooseHeight = 100;
    public static List<String> apiService = new ArrayList<>();

    private List<String> chooseApi = new ArrayList<>();
    public static List<Boolean> booleans = new ArrayList<>();
    private int countApi = 0;
    private int countTrue=0;



    public Window() {
        this.setSize(windowWidth, WindowHeight);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        apiService.add("Jokes");
        apiService.add("country");
        apiService.add("weather");
        apiService.add("quotes");
        apiService.add("numbers");

        for (int i = 0; i < 5; i++) {
            booleans.add(false);
        }

        JButton buttonsApi = new JButton(apiService.get(0));
        this.add(buttonsApi);
        buttonsApi.setBounds((windowWidth - buttonWidth) / 4, (WindowHeight - buttonHeight) / 4, buttonWidth, buttonHeight);
        buttonsApi.addActionListener((e -> {
            if (countApi == 4) {
                countApi = 0;
            } else {
                countApi++;
            }
            buttonsApi.setText(this.apiService.get(countApi));

        }));

        JButton addApi = new JButton("add api");
        this.add(addApi);
        addApi.setBounds((windowWidth - buttonWidth) / 4 + buttonWidth + 50, (WindowHeight - buttonHeight) / 4, buttonWidth, buttonHeight);

        JLabel choose = new JLabel("you choose:" + chooseApi);
        this.add(choose);
        choose.setForeground(Color.BLUE);
        choose.setBounds(((windowWidth - chooseWidth) / 4) - 100, ((WindowHeight - buttonHeight) / 4) - 100, chooseWidth, chooseHeight);
        choose.setFont(new Font("Ariel", 2, 20));
        addApi.addActionListener((e) -> {
            if (!booleans.get(countApi)&&countTrue<3){
            booleans.set(countApi, true);
            chooseApi.add(apiService.get(countApi));
            choose.setText("you choose:" + chooseApi);
           // System.out.println(booleans);
            countTrue++;
        }
        });


        JButton removeApi = new JButton("remove api");
        this.add(removeApi);
        removeApi.setBounds((windowWidth - buttonWidth) / 4 - buttonWidth - 50, (WindowHeight - buttonHeight) / 4, buttonWidth, buttonHeight);

        removeApi.addActionListener((e) -> {
            if (booleans.get(countApi)) {
                booleans.set(countApi, false);
                chooseApi.remove(apiService.get(countApi));
                choose.setText("you choose:" + chooseApi);
                //System.out.println(booleans);
                countTrue--;
            }
        });

    }



}
