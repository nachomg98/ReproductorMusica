package com.example.reproductor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.reproductor.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MediaPlayer mp;
    private Random rnd= new Random();

    private ArrayList<MediaPlayer> vectorMp= new ArrayList<>();
    private ArrayList<R> listMusic= new ArrayList<>();

    int repetir=2;
    int posicion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InitializeMusic();

        binding.btPlay.setOnClickListener(v -> PlayPause(v));

        binding.btRepeat.setOnClickListener(v -> Repeat(v));

        binding.btAfter.setOnClickListener(v -> NextMusic(v));

        binding.btBefore.setOnClickListener(v -> PreviousMusic(v));

        binding.btRandom.setOnClickListener(v -> RandomMusic(v));

    }

    private void InitializeMusic(){
        vectorMp.add(MediaPlayer.create(this,R.raw.race));
        vectorMp.add(MediaPlayer.create(this,R.raw.tea));
        vectorMp.add(MediaPlayer.create(this,R.raw.sound));
    }

    public void PlayPause(View view){
        if(vectorMp.get(posicion).isPlaying()){
            vectorMp.get(posicion).pause();
            binding.btPlay.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
        }
        else {
            vectorMp.get(posicion).start();
            binding.btPlay.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }
    }

    public void Repeat(View view){
        if (repetir==1){
            binding.btRepeat.setBackgroundResource(R.drawable.no_repetir);
            Toast.makeText(this,"No repeat",Toast.LENGTH_SHORT).show();
            repetir=2;
        }
        else{
            binding.btRepeat.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this,"Repeat",Toast.LENGTH_SHORT).show();
            vectorMp.get(posicion).setLooping(true);
            repetir=1;
        }
    }

    public void NextMusic(View view){
        if(posicion<vectorMp.size()-1){
            if(vectorMp.get(posicion).isPlaying()){
                vectorMp.get(posicion).stop();
                posicion++;
                vectorMp.get(posicion).start();

                switch (posicion){
                    case 0:
                        binding.imgMusic.setImageResource(R.drawable.portada1);
                        break;
                    case 1:
                        binding.imgMusic.setImageResource(R.drawable.portada2);
                        break;
                    case 2:
                        binding.imgMusic.setImageResource(R.drawable.portada3);
                        break;
                }
            }
            else {
                posicion++;

                switch (posicion){
                    case 0:
                        binding.imgMusic.setImageResource(R.drawable.portada1);
                        break;
                    case 1:
                        binding.imgMusic.setImageResource(R.drawable.portada2);
                        break;
                    case 2:
                        binding.imgMusic.setImageResource(R.drawable.portada3);
                        break;
                }
            }
        }
        else{
            Toast.makeText(this,"No exists more music",Toast.LENGTH_SHORT).show();
        }
    }

    public void PreviousMusic(View view){
        if(posicion>=1){
            if(vectorMp.get(posicion).isPlaying()){
                vectorMp.get(posicion).stop();

                InitializeMusic();

                posicion--;

                switch (posicion){
                    case 0:
                        binding.imgMusic.setImageResource(R.drawable.portada1);
                        break;
                    case 1:
                        binding.imgMusic.setImageResource(R.drawable.portada2);
                        break;
                    case 2:
                        binding.imgMusic.setImageResource(R.drawable.portada3);
                        break;
                }
                vectorMp.get(posicion).start();
            }
            else{
                posicion--;

                switch (posicion){
                    case 0:
                        binding.imgMusic.setImageResource(R.drawable.portada1);
                        break;
                    case 1:
                        binding.imgMusic.setImageResource(R.drawable.portada2);
                        break;
                    case 2:
                        binding.imgMusic.setImageResource(R.drawable.portada3);
                        break;
                }
            }
        }
        else{
            Toast.makeText(this,"No exists more music",Toast.LENGTH_SHORT).show();
        }
    }

    public void RandomMusic(View view){
        binding.btRandom.setBackgroundResource(R.drawable.randomiconclick);
        Toast.makeText(this,"Random Music",Toast.LENGTH_SHORT).show();
        //Si la musica está sonando
        if(vectorMp.get(posicion).isPlaying()){
            //Para
            vectorMp.get(posicion).stop();
            //Randomiza
            InitializeMusic();

            posicion= rnd.nextInt(vectorMp.size());
            //Inicia
            vectorMp.get(posicion).start();

            switch (posicion){
                case 0:
                    binding.imgMusic.setImageResource(R.drawable.portada1);
                    break;
                case 1:
                    binding.imgMusic.setImageResource(R.drawable.portada2);
                    break;
                case 2:
                    binding.imgMusic.setImageResource(R.drawable.portada3);
                    break;
            }
        }
        else {
            //Si la música está parada randomiza.
            posicion= rnd.nextInt(vectorMp.size());
            vectorMp.get(posicion).start();
            binding.btPlay.setBackgroundResource(R.drawable.pausa);

            switch (posicion){
                case 0:
                    binding.imgMusic.setImageResource(R.drawable.portada1);
                    break;
                case 1:
                    binding.imgMusic.setImageResource(R.drawable.portada2);
                    break;
                case 2:
                    binding.imgMusic.setImageResource(R.drawable.portada3);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vectorMp.get(posicion).stop();
    }
}