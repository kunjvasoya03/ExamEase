package com.example.examease2;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    public static int g_selected_cat_index=0;
    public static int g_selected_test_index=0;
    public static final int NOT_VISITED=0;
    public static final int UNANSWERED=1;
    public static final int ANSWERED=2;
    public static final int REVIEW=3;
    public static List<QuestionsModel> g_queModelList=new ArrayList<>();
   public static FirebaseFirestore g_firestore ;
    public static List<HomeModel>g_homeModelList=new ArrayList<>();
    public static List <TestModel> g_testModelList=new ArrayList<>();
    public static ProfileModel myProfile=new ProfileModel("NA",null);

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

    public static void loadTestData(final MyCompleteListener completeListener){
        g_testModelList.clear();
        g_firestore.collection("QUIZ").document(g_homeModelList.get(g_selected_cat_index).getDOC_ID())
                .collection("TESTSLIST").document("TEST_INFO")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        int noOfTests= Integer.parseInt(g_homeModelList.get(g_selected_cat_index).getNoOfTests());
                        for (int i=1;i<=noOfTests;i++)
                        {
                            g_testModelList.add(new TestModel(
                                    documentSnapshot.getString("TEST"+i+"_ID"),
                                    0,documentSnapshot.getLong("TEST"+i+"_TIME").intValue()
                            ));
                        }

                        completeListener.onSuccess();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailiure();
                    }
                });

    }
    public static void getUserData(MyCompleteListener completeListener)
    {
        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        myProfile.setName(documentSnapshot.getString("NAME"));
                        myProfile.setMail(documentSnapshot.getString("EMAIL_ID"));
                        completeListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailiure();
                    }
                });
    }
    public static void loadData(MyCompleteListener completeListener)
    {
        loadHome(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                getUserData(completeListener);
            }

            @Override
            public void onFailiure() {
                completeListener.onFailiure();
            }
        });
    }
    public static void loadQuestions(MyCompleteListener completeListener)
    {
        g_queModelList.clear();
        g_firestore.collection("QUESTIONS").whereEqualTo("CATEGORY",g_homeModelList.get(g_selected_cat_index).getDOC_ID())
                .whereEqualTo("TEST",g_testModelList.get(g_selected_test_index).getTestid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot doc:queryDocumentSnapshots
                             ) {
                            g_queModelList.add(new QuestionsModel(
                                    doc.getString("QUESTION"),
                                    doc.getString("A"),
                                    doc.getString("B"),
                                    doc.getString("C"),
                                    doc.getString("D"),
                                    doc.getLong("ANSWER").intValue(),
                                    -1,
                                    NOT_VISITED
                            ));

                        }
                        completeListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completeListener.onFailiure();
                    }
                });
    }

}
