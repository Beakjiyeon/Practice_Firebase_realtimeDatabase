package com.example.fb1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2;
    TextView tv;


    // 데이터베이스 참조를 M 루트 참조에 입력
    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
    // Ref라고 이름 붙인 이유 : 참조를 가지게 될때 참조를 파이어베이스 제이슨 트리의 루트로 얻기 때문이다.
// 차일드 참조를 생성한다.
    // = 상태 참조=루트 참조 : .차일드 를 호출하고 문자열 상태를 입력
    DatabaseReference mConditionRef=mRootRef.child("condition");
    // 루트에 차일드를 생성하고
    // 루트 아래에서 상태의 위치를 생성한다.
    // /-condition
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        tv=(TextView)findViewById(R.id.tv);

        // 데이터베이스의 상태가 바뀌면
        // 텍스트의 텍스트 속성이 업데이트 되도록 한다.
        // 온스타트 라이프 사이클 메소드에 벨류 리스너를 생성한다.
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 상태 : /condition : 현재값을 변화시킴
                mConditionRef.setValue("hello");
                // 설정값을 호출한다.
                // 실시간 데이터 베이스를 업데이트를 한다.
                // (onstart 메소드 안에 있는
                // add밸류이벤트리스너 안에 있는)
                // 실시간 데이터 베이스가 업데이트 되면
                // onDataChanged 메소드가 작동된다. 실시간 리스너에서

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionRef.setValue("bye");

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        // 벨류 이벤트 리스너를 여기에 붙인다. 중요!
        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 실시간 상태가 바뀔때마다 호출
                // 데이터 스냅샷으로 데이터를 다시 받음
                // 데이터를 문자열로 받겠음
                String text=dataSnapshot.getValue(String.class);// 문자열로 마들기 위해 String.class
                // 텍스트뷸 가서 텍스트에 입력하여 설정텍스트를 호출
                tv.setText(text);
                // 보안규칙 : 인증된 사용자만 데이터ㅔ 접근 가능함
                // 규칙탭 변경


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러가 날때마다 작동
            }
        });

    }
}
