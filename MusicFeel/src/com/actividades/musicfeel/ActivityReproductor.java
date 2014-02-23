package com.actividades.musicfeel;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;



public class ActivityReproductor extends Activity {

	private MediaPlayer reproductor;
	private ImageButton play;
	private ImageButton pause;
	private TextView nombre;
	
	private boolean first=false;
	private int savePos=0;
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_reproductor);
		play=(ImageButton) findViewById(R.id.ic_media_play);
		pause=(ImageButton)findViewById(R.id.ic_media_pause);
		nombre=(TextView) findViewById(R.id.nombre_cancion);
		nombre.setSelected(true);
		if(bundle!=null){
		first=bundle.getBoolean("first");
		savePos=bundle.getInt("posicion");
		}
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				play();
			}
		});
		
		pause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reproductor.pause();
			}
		});
		
		reproductor= new MediaPlayer();
		
		Bundle datos=getIntent().getExtras();
		if(datos!=null){
			try {
				first=true;
				nombre.setText(datos.getString("nombre"));
				reproductor.setDataSource(datos.getString("resultado"));
				reproductor.prepare();
				reproductor.seekTo(savePos);
				reproductor.start();
				
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	protected void onPause(){
		super.onPause();
		reproductor.pause();
		}
	
	
	@Override
	   protected void onSaveInstanceState(Bundle guardarEstado) {
	         super.onSaveInstanceState(guardarEstado);
	         if (reproductor != null) {
	                int pos = reproductor.getCurrentPosition();
	                guardarEstado.putInt("posicion", pos);
	         }
	   }
	
	@Override
	   protected void onRestoreInstanceState(Bundle recEstado) {
	         super.onRestoreInstanceState(recEstado);
	         if (recEstado != null) {
	                savePos = recEstado.getInt("posicion");
	         }
	   }
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onDestroy() {
        super.onDestroy();
        if (reproductor != null) {
               reproductor.release();
               reproductor = null;
        }
	}
	public void play(){
		try {
			reproductor.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reproductor.start();
	}
	

}