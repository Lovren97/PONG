package hr.fer.ruazosa.pong.Multiplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import hr.fer.ruazosa.pong.Online;
import hr.fer.ruazosa.pong.OnlineGame;
import hr.fer.ruazosa.pong.OnlineService;
import hr.fer.ruazosa.pong.Singleplayer.Bar;
import hr.fer.ruazosa.pong.Singleplayer.GameView;

/**
 * Created by Ivan Lovrencic on 22.6.2018..
 */

public class UDPServer {

    private Thread thread;
    private boolean running = true;

    public void startServerSocket(final Online online, final Activity activity) {

        thread = new Thread(new Runnable() {

            private String stringData = null;
            @Override
            public void run() {
                Log.d("Stvoren sam!","Jesi tu?");
                while(running){
                    byte[] msg = new byte[1024];
                    DatagramPacket dp = new DatagramPacket(msg, msg.length);
                    DatagramSocket ds = null;
                    try {
                        ds = new DatagramSocket(9001);
                        //ds.setSoTimeout(50000);
                        ds.receive(dp);

                        stringData = new String(msg, 0, dp.getLength());
                       // Log.d("Tu sam!",stringData);
                        if(stringData.equals("Connected") && online.getConnected()){
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    online.updateUI("Connected!");
                                    online.startGame();
                                }
                            });
                        }
                        else if(stringData.equals("Connected")){
                            Log.d("Ipak sam ovdje!","Drugi if");
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    online.setConnected(true);
                                }
                            });
                        }
                        else if(stringData.equals("STOP")){
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    online.setConnected(false);
                                }
                            });
                        }
                        else if(stringData.equals("END")){
                            OnlineService.activity.finish();
                        }
                        else {
                            if(stringData.startsWith("P")){
                                Log.d("Doslo je do promjene!",stringData);
                                String[] data = stringData.split(",");
                                String position = data[1];
                                try{
                                    OnlineService.game.onTouchEventOpponent(position);
                                }catch (NullPointerException e){
                                    e.printStackTrace();
                                }
                            }
                            if(stringData.startsWith("B")){
                                String[] data = stringData.split(",");
                                final String x = data[1];
                                final String y = data[2];
                                final Bar.Position position;
                                if(data[3].equals("L")){
                                    position = Bar.Position.LEFT;
                                }
                                else{
                                    position = Bar.Position.RIGHT;
                                }
                                final int playerScore = Integer.parseInt(data[4]);
                                final int computerScore = Integer.parseInt(data[5]);
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(OnlineService.ball != null){
                                                OnlineService.ball.newPosition(Float.parseFloat(x),Float.parseFloat(y),position);
                                                OnlineService.game.getPlayer().setScore(playerScore);
                                                OnlineService.game.getComputer().setScore(computerScore);
                                        }
                                    }
                                });
                                thread.start();
                            }

                            /*if(stringData.startsWith("Score")){
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] data = stringData.split(",");
                                        if(!(data[0].equals("P"))){
                                            try {
                                                OnlineService.game.getPlayer().setScore(Integer.parseInt(data[1]));
                                                OnlineService.game.getComputer().setScore(Integer.parseInt(data[2]));
                                            }catch (Exception e){
                                                Log.d("Neispravan rezultat",stringData);
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                                thread.start();
                            }*/
                        }
                    } catch (IOException e) {
                        running = false;
                        e.printStackTrace();
                    } finally {
                        if (ds != null) {
                            ds.close();
                        }
                    }
                }
            }


        });
        thread.start();
    }

    public void startOnlineGame(final MultiplayerGame game){
        thread = new Thread(new Runnable() {

            private String stringData = null;

            @Override
            public void run() {

                while(running){
                    byte[] msg = new byte[1024];
                    DatagramPacket dp = new DatagramPacket(msg, msg.length);
                    DatagramSocket ds = null;
                    try {
                        ds = new DatagramSocket(9001);
                        //ds.setSoTimeout(50000);
                        ds.receive(dp);

                        stringData = new String(msg, 0, dp.getLength());
                        Log.d("Tu sam!",stringData);
                        if(stringData.startsWith("P")){
                            Log.d("Doslo je do promjene!",stringData);
                            String[] data = stringData.split(",");
                            String position = data[1];
                            game.onTouchEventOpponent(position);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        running = false;
                    } finally {
                        if (ds != null) {
                            ds.close();
                        }
                    }
                }
            }


        });
        thread.start();
    }

    public void stopThread(){

        Thread stopping = new Thread(new Runnable() {
            @Override
            public void run() {
                while(thread != null){
                    running = false;
                    try{
                        thread.join();
                        thread = null;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        stopping.start();
    }

    public void startRunning(){
        running = true;
    }
}
