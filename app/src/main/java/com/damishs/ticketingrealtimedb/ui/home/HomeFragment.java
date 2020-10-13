package com.damishs.ticketingrealtimedb.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.damishs.ticketingrealtimedb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    EditText editTextName;
    Button buttonAdd;
    Spinner spinnerGenres;

    DatabaseReference databaseArtists;

    ListView listViewArtist;

    List<Artist> artistList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        editTextName = root.findViewById(R.id.editTextName);
        buttonAdd = root.findViewById(R.id.buttonAddArtist);
        spinnerGenres = root.findViewById(R.id.spinnerGenres);

        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        listViewArtist = root.findViewById(R.id.listViewArtist);

        artistList = new ArrayList<>();

//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });

        listViewArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Artist artist = artistList.get(i);



                showUpdateDialog(artist.getArtistID(), artist.getArtistName());

                return false;
            }
        });


        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                artistList.clear();

                for (DataSnapshot artistSnapShot : snapshot.getChildren()) {
                    Artist artist = artistSnapShot.getValue(Artist.class);
                    artistList.add(artist);
                }

                ArrayAdapter adapter = new ArtistList(getActivity(), artistList);
                listViewArtist.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }


    private void showUpdateDialog(final String artistId, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);
        final Spinner spinnerGenres = dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);
        final Button buttonUpdateGenre = dialogView.findViewById(R.id.buttonUpdateGenre);


        dialogBuilder.setTitle("Updating Artist " + artistName);
        editTextName.setText(artistName);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenres.getSelectedItem().toString();

                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Name Required");
                    return;
                } else {
                    updateArtist(artistId, name, genre);
                }
                alertDialog.dismiss();
            }

        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                deleteArtist(artistId);
                alertDialog.dismiss();
            }

        });


        buttonUpdateGenre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                updateGenre(artistId,"Default Genre");
                alertDialog.dismiss();
            }

        });



    }


    private void deleteArtist(String artistId) {

        DatabaseReference drArtist = FirebaseDatabase.getInstance().getReference("artists").child(artistId);

        drArtist.removeValue();

        Toast.makeText(this.getContext(), "Artist is Deleted", Toast.LENGTH_SHORT).show();

    }


    private boolean updateArtist(String id, String name, String genre) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists").child(id);

        Artist artist = new Artist(id, name, genre);

        databaseReference.setValue(artist);

        Toast.makeText(this.getContext(), "Artist Updated Sucessfully", Toast.LENGTH_SHORT).show();

        return true;
    }

    private boolean updateGenre(String id,String genre) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists").child(id).child("artistGenre");

        databaseReference.setValue(genre);

        Toast.makeText(this.getContext(), "Genre Updated Sucessfully", Toast.LENGTH_SHORT).show();

        return true;
    }


    public void addArtist() {

        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString().trim();


        if (!TextUtils.isEmpty(name)) {

            //unique id is generated
            String id = databaseArtists.push().getKey();
            Artist artist = new Artist(id, name, genre);

            //overwrite data to created id
            databaseArtists.child(id).setValue(artist);
            Toast.makeText(this.getContext(), "Artist added", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this.getContext(), "You should enter name!!!", Toast.LENGTH_SHORT).show();
        }

    }

}
