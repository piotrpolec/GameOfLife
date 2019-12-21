package Visualizers;

import Game.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorldVisualizer {
  JFrame frame;
  JTextArea textArea;
  JButton pauseContinueButton;
  JButton quitButton;
  Timer timer;
  World world;



  public WorldVisualizer(World world){
    this.world = world;
    this.initialize();
  }

  private void initialize(){
    this.frame = new JFrame("Animals simulation");
    frame.setSize(800,800);
    frame.setLayout(null);
    frame.setVisible(true);

    this.textArea=new JTextArea("");
    textArea.setBounds(100,100, 550,650);
    textArea.setEditable(false);

    this.pauseContinueButton = new JButton("");
    pauseContinueButton.setBounds(650,200,100,20);
    frame.add(pauseContinueButton);

    pauseContinueButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        if (timer == null)
          startVisualization();
        else
          pauseVisualization();
      }
    });

    this.quitButton = new JButton("Quit");
    quitButton.setBounds(650,230,100,20);
    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
      }
    });

    frame.add(pauseContinueButton);
    frame.add(quitButton);

  }
  public void startVisualization(){
    if (timer == null) {
      timer = new Timer(75, timerListener);
      timer.start();
      pauseContinueButton.setText("Pause");
    }
  }

  private void pauseVisualization() {
    if (timer != null) {
      timer.stop();
      timer = null;
      pauseContinueButton.setText("Continue");
    }
  }

  ActionListener timerListener = new ActionListener() {
    public void actionPerformed(ActionEvent event) {
      textArea.setText(world.toString());
      if(world.animalsExtinct()){
        pauseContinueButton.setVisible(false);
        timer.stop();
      }
      frame.add(textArea);
      SwingUtilities.updateComponentTreeUI(frame);
      world.passTheDay();
    }
  };


}
