package com.mehmetsakiratasayin.football.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.mehmetsakiratasayin.football.R;
import com.mehmetsakiratasayin.football.adapter.RecyclerViewAdapter;
import com.mehmetsakiratasayin.football.adapter.ViewPagerAdapter;
import com.mehmetsakiratasayin.football.adapter.ViewPagerAdapter2;
import com.mehmetsakiratasayin.football.model.Teams;
import com.mehmetsakiratasayin.football.service.Interface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {

    private ArrayList<Teams> teamsmodel;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    private  String BASE_URL="https://raw.githubusercontent.com/";
    Retrofit retrofit;
    ListView listView;
    TextView lastName,firsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        listView=findViewById(R.id.list_item);

        Gson gson =new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
               getdata();
    }
    public void viewGetData(){
        lastName=findViewById(R.id.lastText);
        firsName=findViewById(R.id.firtText);
        firsName.setText("Lig İlk Yarı (Yön Değiştirme Butonu: ←↕→ )");
        lastName.setText("Lig İkinci Yarı (Yön Değiştirme Butonu: ←↕→ )");
        Interface inter =retrofit.create(Interface.class);
        retrofit2.Call<List<Teams>> call = inter.getData();

        call.enqueue(new Callback<List<Teams>>() {
            @Override
            public void onResponse(Call<List<Teams>> call, Response<List<Teams>> response) {
                if(response.isSuccessful()){

                    List<Teams> teams = response.body();
                    teamsmodel = new ArrayList<>(teams);

                    // First Half
                    int teamSize=teams.size();
                    int roundCount=teamSize-1;
                    int matchCountPerRound=teamSize/2;
                    List<String> teamsList=new ArrayList();
                    List<String> weekList=new ArrayList();
                    ArrayList<String> deneme1=new ArrayList();
                    ArrayList<String> deneme2=new ArrayList();
                    List<String> allList=new ArrayList();
                    List<String> allList2=new ArrayList();
                    for (Teams Model : teamsmodel) {
                        teamsList.add(Model.isp1);
                    }
                    for (int i = 0; i < roundCount; i++) {
                        weekList.add(i+".hafta");
                        System.out.println(i+".hafta");

                        for(int j=0;j<matchCountPerRound;j++){

                            int firstIndex=j;
                            int secondIndex=(teamSize-1)-j;
                         String   teamText=teamsList.get(firstIndex);
                         String   teamText2=teamsList.get(secondIndex);
                            deneme1.add(teamText);
                            deneme2.add(teamText2);
                        }


                        ArrayList<String> newList=new ArrayList<String>();


                        newList.add(teamsList.get(0));


                        newList.add(teamsList.get(teamsList.size()-1));

                        for(int k=1;k<teamsList.size()-1;k++){
                            newList.add(teamsList.get(k));
                        }


                        teamsList=newList;

                    }
                        //Last Half
                    for (int i = 0; i < roundCount; i++) {
                        weekList.add(i+".hafta");
                        System.out.println(i+".hafta");

                        for(int j=0;j<matchCountPerRound;j++){

                            int firstIndex=j;
                            int secondIndex=(teamSize-1)-j;
                            String   teamText=teamsList.get(firstIndex);
                            String   teamText2=teamsList.get(secondIndex);
                            deneme1.add(teamText2);
                            deneme2.add(teamText);


                        }


                        ArrayList<String> newList=new ArrayList<String>();
                        newList.add(teamsList.get(0));
                        newList.add(teamsList.get(teamsList.size()-1));
                        for(int k=1;k<teamsList.size()-1;k++){
                            newList.add(teamsList.get(k));
                        }
                        teamsList=newList;
                    }

                    System.out.println("Toplam Hafta Sayısı : "+roundCount);
                    System.out.println("Bir Hafta'daki Maç Sayısı:  "+matchCountPerRound);
int week=0;

for(int i=0;i<(roundCount*matchCountPerRound);i++){

    if(i%matchCountPerRound==0){
       week++;
    }
    allList.add(week+".Hafta \n "+deneme1.get(i)+" - "+deneme2.get(i));

}
for(int k=roundCount*matchCountPerRound;k<(roundCount*matchCountPerRound)*2;k++){
    if(k%matchCountPerRound==0){
        week++;
    }
    allList2.add(week+".Hafta \n "+deneme1.get(k)+" - "+deneme2.get(k));
}

ViewPager2 viewPager2 = findViewById(R.id.viewPager2_);
ViewPager2 viewPager2two= findViewById(R.id.viewPager2_two);

viewPager2two.setAdapter(new ViewPagerAdapter2(MainActivity.this,allList2, viewPager2two));
viewPager2.setAdapter(new ViewPagerAdapter(MainActivity.this,allList, viewPager2));
                }
            }

            @Override
            public void onFailure(Call<List<Teams>> call, Throwable t) {
                t.printStackTrace();
            }
        } );

    }

    public void getdata(){
        Interface inter =retrofit.create(Interface.class);
        retrofit2.Call<List<Teams>> call = inter.getData();
        call.enqueue(new Callback<List<Teams>>() {
            @Override
            public void onResponse(Call<List<Teams>> call, Response<List<Teams>> response) {
                if(response.isSuccessful()){
                    List<Teams> teams = response.body();
                    teamsmodel = new ArrayList<>(teams);

                    ArrayList<String> teamsStringList=new ArrayList();
                    List<String> list = new ArrayList<>();
                    for (Teams Model : teamsmodel) {
                        teamsStringList.add(Model.isp1);
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(teamsmodel);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Teams>> call, Throwable t) {

            }
        } );


}

    public void fixButton(View view) {

        viewGetData();
    }
}