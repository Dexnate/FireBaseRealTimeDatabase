package mk.fr.firebaserealtimedatabase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mk.fr.firebaserealtimedatabase.Model.Author;
import mk.fr.firebaserealtimedatabase.Model.Book;

public class MainActivity extends AppCompatActivity {

   private FirebaseDatabase firebaseDatabase;
   private DatabaseReference bookReference;
   private List<Book> bookList;
   private ListView bookListView;
   private BookArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        bookReference = firebaseDatabase.getReference().child("books");

        bookListView = findViewById(R.id.bookList);
        adapter = new BookArrayAdapter(this, R.layout.booklistitem, bookList);
        bookListView.setAdapter(adapter);

        //Instanciation de la liste
        bookList = new ArrayList<>();

        //Récupération des données avec abonnement aux modifications ultérieures
        bookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //réinitialiser la list des livres
                bookList.clear();

                //Boucler sur l'ensemble des noeuds
                for( DataSnapshot bookSnapshot : dataSnapshot.getChildren()){
                    //Création d'une instance de book et hydratation avec les données du snapshot
                    Book book = bookSnapshot.getValue(Book.class);
                    //Ajout du livre à la liste
                    bookList.add(book);
                }
                Log.d("Main", "Fin de récupération de données");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("Main", "Fin de onCreate");
      //  addBooks();


    }

    private void addBooks() {
        //Création d'un livre

        Author king = new Author("King", "Stephen", "Americain");
        Author verne = new Author("Verne", "Jules", "Français");

        String bookId = bookReference.push().getKey();
        Book book = new Book("22/11/63", 25.0, king);
        bookReference.child(bookId).setValue(book);

        bookId = bookReference.push().getKey();
        book = new Book("IT", 15.0, king);
        bookReference.child(bookId).setValue(book);

        bookId = bookReference.push().getKey();
        book = new Book("20000 lieux sous les mers", 20.0, verne);
        bookReference.child(bookId).setValue(book);
    }



    private class BookArrayAdapter extends ArrayAdapter<Book>{

        private Activity context;
        private int resource;
        List<Book> data;

        public BookArrayAdapter(@NonNull Activity context, int resource, @NonNull List<Book> data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.data = data;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View view = context.getLayoutInflater().inflate(this.resource, parent, false);

            Book currentBook = bookList.get(position);
            TextView textView = view.findViewById(R.id.bookListText);
            textView.setText(currentBook.getTitle()+ " par" + currentBook.getAuthor().getName());

            return view;
        }
    }




}
