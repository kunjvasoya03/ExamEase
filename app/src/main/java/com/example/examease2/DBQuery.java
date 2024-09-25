package com.example.examease2;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBQuery {
   public static FirebaseFirestore g_firestore ;
    public static List<HomeModel>g_homeModelList=new ArrayList<>();
   public static void createuserData(String email,String name,MyCompleteListener completeListener) {
       Map<String, Object> userData = new ArrayMap<>();
       userData.put("EMAIL_ID", email);
       userData.put("NAME", name);
       userData.put("TOTAL_SCORE", 0);

       DocumentReference userDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

       WriteBatch batch = g_firestore.batch();
       batch.set(userDoc, userData);

       DocumentReference countDoc = g_firestore.collection("USERS").document("TOTAL_USERS");
       batch.update(countDoc, "COUNT", FieldValue.increment(1));

       batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {

               completeListener.onSuccess();

           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {

                completeListener.onFailiure();

           }
       });
   }
   public static void loadHome(MyCompleteListener completeListener)
   {
       g_homeModelList.clear();
       g_firestore.collection("QUIZ").get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       Map<String, QueryDocumentSnapshot>docList=new ArrayMap<>();
                       for (QueryDocumentSnapshot doc:queryDocumentSnapshots) {
                           docList.put(doc.getId(),doc );
                       }
                       QueryDocumentSnapshot homeListdoc=docList.get("CATEGORIES");
                       int homeCount=homeListdoc.getLong("COUNT").intValue();
                       for (int i=1;i<=homeCount;i++)
                       {
                           String catId=homeListdoc.getString("CAT"+i+"_ID");
                           QueryDocumentSnapshot catDoc=docList.get(catId);
                           int noOfTests=catDoc.getLong("NO_OF_TESTS").intValue();
                           String catName=catDoc.getString("NAME");
                           g_homeModelList.add(new HomeModel(catId,catName,noOfTests));
                           completeListener.onSuccess();
                       }
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                        completeListener.onFailiure();
                   }
               });
   }
}
