package com.company;

import com.company.algo.dijkstra.DijkstraAlgorithmChecker;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Stage extends JFrame{
    private JTextField textField1;
    private JButton submitButton;
    JPanel panelMain;
    private JLabel stageType;
    private JLabel gambar;
    private JLabel scoreLabel;
    private JLabel lifeLabel;
    private int scoreGain; // score gain
    // for randomly selecting the file name
    private int randomImageFileName;

    public Stage(boolean visibility) {
        // for initialization
        setContentPane(panelMain);
        setTitle("Stage");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        pack();
        setSize(1000,800);
        setLocationRelativeTo(null);
        setVisible(visibility);

        // set score
        scoreGain = 5;
        updateLife();
        updateScore();


        // init listener for submit
        addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                Main.frameConnector.setVisible(true);
            }
        });

        // set top-right text
        stageType.setText(Main.stageTypeConnector);

        // set jlabel icon
        Random randomizer = new Random();
        randomImageFileName = randomizer.nextInt(Objects.requireNonNull(new File("src/com/company/soal/gambar").list()).length);
        gambar.setIcon(new ImageIcon(String.format("src/com/company/soal/gambar/%d.png", randomImageFileName)));

        // clear text
        gambar.setText("");

        // Listener
        submitButton.addActionListener(e -> {
            // Submit button
            try {
                if (checkAnswer(textField1.getText().toLowerCase())) {
                    // correct
                    System.out.println("Correct!");
                    // score add
                    Main.score += scoreGain;
                    updateScore();

                } else {
                    // incorrect
                    System.out.println("Incorrect!");
                    // life subtract
                    Main.life -= 1;
                    updateLife();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // helper function for updating
    private void updateScore() {
        scoreLabel.setText(String.format("%d", Main.score));
    }

    private void updateLife() {
        lifeLabel.setText(String.format("%d", Main.life));

        // check if dead
        if (Main.life <= 0) {
            setVisible(false);
            Losing losing = new Losing(true);
        }

    }
    // function for checking answer
    private boolean checkAnswer(String answer) throws IOException {
        String type = Main.stageTypeConnector;

        switch (type) {
            case "Breadth First Search":

                break;
            case "Depth First Search":
                break;
            case "Shortest Path":
                // Implementing Dijkstra Algorithm here
                DijkstraAlgorithmChecker checker = new DijkstraAlgorithmChecker(randomImageFileName);
                return checker.getReturnValue(answer);
            default:
                System.out.println("Error, stage type not found!");
        }

        // for error case
        System.out.println("ERROR, Checking answer failed");
        return false;
    }



}
