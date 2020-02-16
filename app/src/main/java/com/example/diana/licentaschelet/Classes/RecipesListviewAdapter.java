package com.example.diana.licentaschelet.Classes;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diana.licentaschelet.R;

import java.util.ArrayList;

public class RecipesListviewAdapter extends ArrayAdapter<Reteta> implements View.OnClickListener {

    private ArrayList<Reteta> listaRetete;
    Context context;

    public RecipesListviewAdapter(@NonNull Context context, ArrayList<Reteta> listaRetete) {
        super(context,0, listaRetete);
        this.context=context;
        this.listaRetete = listaRetete;
    }

    @Override
    public void onClick(View view) {
        int position=(Integer) view.getTag();
        Object object= getItem(position);
        Reteta r=(Reteta)object;

        Uri uri = Uri.parse(r.getRecipeLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);

        Toast.makeText(context,"onclick link",Toast.LENGTH_SHORT).show();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);


        Bitmap bitmap=null;// = BitmapFactory.decodeResource(context.getResources(),R.drawable.defaultimg);
        Reteta r = listaRetete.get(position);

        ImageView image = listItem.findViewById(R.id.ivReteta);

        ProgressDialog progressDialog = null;
        try {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Downloading images...");
            progressDialog.show();
            ImagesDownload download = new ImagesDownload(r.getImageLink());
            bitmap = download.execute().get();

        } catch (Exception e) {
            e.printStackTrace();
        }


        progressDialog.dismiss();
        if(bitmap == null)
             bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.defaultimg);
        image.setImageBitmap(bitmap);



        TextView name =  listItem.findViewById(R.id.tvName);
        name.setText(r.getTitle());

        TextView ingredients = listItem.findViewById(R.id.tvIngredients);
        ingredients.setText(r.getIngredients().substring(0,r.getIngredients().length()-2));
       // ingredients.setText("alalalal");
        return listItem;



    }






}


