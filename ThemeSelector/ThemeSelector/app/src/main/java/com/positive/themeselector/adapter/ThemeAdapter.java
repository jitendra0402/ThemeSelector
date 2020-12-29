package com.positive.themeselector.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.positive.themeselector.DatabaseManager;
import com.positive.themeselector.R;
import com.positive.themeselector.model.Theme;
import com.positive.themeselector.utils.ConstantUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ThemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    DatabaseManager databaseManager;
    private List<Theme> themeArrayList = new ArrayList<>();
    Context context;

    public ThemeAdapter(Context context) {
        this.context = context;
        databaseManager = DatabaseManager.getInstance(context);
    }

    public void setSoundArrayList(List<Theme> folders) {
        this.themeArrayList = folders;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewSoundName;

        ProgressBar progressBar;

        ImageView imageViewThumb;
        ImageView imageViewDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewSoundName = itemView.findViewById(R.id.theme_video_name);
            progressBar = itemView.findViewById(R.id.progressBar);

            imageViewThumb = itemView.findViewById(R.id.theme_thumb_image);
            imageViewDownload = itemView.findViewById(R.id.theme_download_image);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_theme, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        Theme folder = themeArrayList.get(position);
        holder1.textViewSoundName.setText(folder.Theme_Name);

        holder1.progressBar.setVisibility(View.GONE);

        if (databaseManager.checkTheme(folder.Id))
            holder1.imageViewDownload.setVisibility(View.GONE);
        else
            holder1.imageViewDownload.setVisibility(View.VISIBLE);


        Glide.with(context).load(folder.Thumnail_Big).into(holder1.imageViewThumb);


        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder1.imageViewDownload.getVisibility() == View.VISIBLE) {
                    holder1.progressBar.setProgress(0);
                    holder1.progressBar.setVisibility(View.VISIBLE);
                    holder1.imageViewDownload.setVisibility(View.GONE);

                    new DownloadThemeTask(holder1, holder1.getLayoutPosition()).execute(themeArrayList.get(holder1.getLayoutPosition()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return themeArrayList.size();
    }

/*    public class DownloadSoundTask extends AsyncTask<Sound, Integer, String> {

        String soundfile = "";
        ViewHolder holder;
        Sound sound;
        int position;

        public DownloadSoundTask(ViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(Sound... Sounds) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            this.sound = Sounds[0];
            try {
                URL url = new URL(Sounds[0].sound_full_url);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Encoding", "identity");
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();
                soundfile = ConstantUtils.GetSoundPath(context) + System.currentTimeMillis() + ".mp3";
                input = connection.getInputStream();
                output = new FileOutputStream(soundfile);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

            } catch (Exception e) {
                Log.e(Application.class.getName(), e.toString());
                return "-1";
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }


                if (connection != null)
                    connection.disconnect();
            }
            return String.valueOf(Sounds[0].sound_name);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            holder.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            holder.progressBar.setVisibility(View.GONE);
            holder.textViewUse.setVisibility(View.VISIBLE);

            if (!s.equalsIgnoreCase("-1")) {
                holder.textViewUse.setText("Use");
                databaseManager.addMusic(sound.id, sound.category_id, sound.sound_full_url, soundfile, sound.lyrics);
                notifyItemChanged(position);
            } else {
                Log.e(Application.class.getName(), "Download Sound Failed");
                Toast.makeText(context, "Download Sound Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }*/


    private class DownloadThemeTask extends AsyncTask<Theme, Integer, String> {

        String soundfile = "", imagefile = "";
        ViewHolder holder;
        Theme theme;
        int position;

        public DownloadThemeTask(ViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Theme... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                this.theme = sUrl[0];

                URL url = new URL(sUrl[0].SoundFile);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Encoding", "identity");
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                soundfile = ConstantUtils.GetSoundPath(context) + System.currentTimeMillis() + ".mp3";

                input = connection.getInputStream();
                output = new FileOutputStream(soundfile);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }


            } catch (Exception e) {
                Log.e(Application.class.getName(), e.toString());
                return "-1";
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }

            return String.valueOf(soundfile);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            holder.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(final String result) {

            if (!result.equalsIgnoreCase("-1")) {
                imagefile = ConstantUtils.GetImagePath(context) + System.currentTimeMillis() + ".png";

                class SaveThisImage extends AsyncTask<Void, Void, Void> {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Void doInBackground(Void... arg0) {
                        try {
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(imagefile);
                                Bitmap bitmap = Picasso.get().load(theme.Thumnail_Big).get();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                intent.setData(Uri.fromFile(new File(imagefile)));
                                context.sendBroadcast(intent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);

                        holder.progressBar.setVisibility(View.GONE);
                        holder.imageViewDownload.setVisibility(View.GONE);

                        databaseManager.addMusic(theme.Id, theme.Theme_Name, String.valueOf(theme.GameobjectName), theme.Thumnail_Big, imagefile, theme.SoundFile, soundfile, theme.lyrics);
                        notifyItemChanged(position);
                    }
                }
                SaveThisImage shareimg = new SaveThisImage();
                shareimg.execute();

            }
        }
    }

}
