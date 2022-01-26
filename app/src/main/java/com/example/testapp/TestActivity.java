package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testapp.databinding.ActivityTestBinding;
import com.example.testapp.model.TestClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class TestActivity extends AppCompatActivity {
    private ActivityTestBinding binding;
    private ArrayList<TestClass> list1;
    private ArrayList<TestClass> list2;
    private ArrayList<TestClass> list3;
    private int counter;
    private int checking;
    private String myAnswer = "";
    private int n_o_r_ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        loadTest();
        counter = 1;
        checking = 0;
        n_o_r_ans = 0;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String key = bundle.getString("key");

        binding.numberOfQuestion.setText(counter + "/15");
        binding.countOfRightAnswers.setText(n_o_r_ans + "");

        if (key.equals("tarix")) {
            createTest(list1);
        } else if (key.equals("ona_tili")) {
            createTest(list3);
        } else if (key.equals("matem")) {
            createTest(list2);
        }

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                checking = 1;
                myAnswer = checkedButton.getText().toString();
            }
        });

        binding.next.setOnClickListener(view -> {
            if (key.equals("tarix")) {
                checkingAnswer(list1);
            } else if (key.equals("ona_tili")) {
                checkingAnswer(list3);
            } else if (key.equals("matem")) {
                checkingAnswer(list2);
            }
        });

        binding.back.setOnClickListener(view -> finish());
    }

    private void checkingAnswer(ArrayList<TestClass> list) {
        if (counter < 15) {
            if (checking == 1) {
                TestClass testClass = list.get(counter - 1);
                String right_answer = testClass.getRight_answer();
                if (right_answer.equals(myAnswer)) {
                    checkingTimer(true, list);
                } else {
                    checkingTimer(false, list);
                }
            } else {
                checkingTimer2();
            }
        } else if (counter == 15) {
            if (checking == 1) {
                TestClass testClass = list.get(counter - 1);
                String right_answer = testClass.getRight_answer();
                if (right_answer.equals(myAnswer)) {
                    checkingTimer(true, list);
                } else {
                    checkingTimer(false, list);
                }
            } else {
                checkingTimer2();
            }
        } else {
            binding.checkingTv.setText("Test savollari tugadi!");
        }
    }

    private void checkingAnswer2(ArrayList<TestClass> list) {
        if (checking == 1) {
            if (list.get(counter - 1).getRight_answer().equals(myAnswer)) {
                checkingTimer(true, list);
            } else {
                checkingTimer(false, list);
            }
        } else {
            checkingTimer3(list);
        }
        /*if (counter < 15) {
            if (checking == 1) {
                TestClass testClass = list.get(counter - 1);
                String right_answer = testClass.getRight_answer();
                if (right_answer.equals(myAnswer)) {
                    checkingTimer(true, list);
                } else {
                    checkingTimer(false, list);
                }
            } else {
                checkingTimer3(list);
            }
        } else if (counter == 15) {
            if (checking == 1) {
                TestClass testClass = list.get(counter - 1);
                String right_answer = testClass.getRight_answer();
                if (right_answer.equals(myAnswer)) {
                    checkingTimer(true, list);
                } else {
                    checkingTimer(false, list);
                }
                binding.checkingTv.setText("Test savollari tugadi!");
            } else {
                binding.checkingTv.setText("Vaqtingiz tugadi!");
            }
        } else {
            binding.checkingTv.setText("Test savollari tugadi!");
        }*/
    }

    private void checkingTimer(boolean bool, ArrayList<TestClass> list) {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                if (bool) {
                    binding.checkingTv.setText("Javobingiz to`g`ri");
                    n_o_r_ans++;
                } else {
                    binding.checkingTv.setText("Javobingiz noto`g`ri");
                }
            }

            @Override
            public void onFinish() {
                binding.countOfRightAnswers.setText(n_o_r_ans + "");
                if (counter < 15) {
                    binding.answer1.setChecked(false);
                    binding.answer2.setChecked(false);
                    binding.answer3.setChecked(false);
                    binding.answer4.setChecked(false);
                    counter++;
                    createTest(list);
                } else if (counter == 15) {
                    binding.checkingTv.setText("Test savollari tugadi!");
                }
            }
        }.start();
    }

    private void checkingTimer2() {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                binding.checkingTv.setText("Javob belgilanmagan!");
            }

            @Override
            public void onFinish() {
                binding.checkingTv.setText("");
            }
        }.start();
    }

    private void checkingTimer3(ArrayList<TestClass> list) {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                binding.checkingTv.setText("Vaqtingiz tugadi!");
            }

            @Override
            public void onFinish() {
                binding.checkingTv.setText("");
                if (counter < 15) {
                    counter++;
                    createTest(list);
                } else if (counter == 15) {
                    binding.checkingTv.setText("Test savollari tugadi!");
                }

/*                if (counter < 15) {
                    counter++;
                    createTest(list);
                } else if (counter == 15) {
                    binding.checkingTv.setText("Test savollari tugadi!");
                }*/
            }
        }.start();
    }

    private void createTest(ArrayList<TestClass> list) {
        binding.checkingTv.setText("");
        checking = 0;
        myAnswer = "";
        binding.numberOfQuestion.setText(counter + "/15");
        TestClass test = list.get(counter - 1);
        binding.question.setText(test.getQuestion());
        binding.answer1.setText(test.getAnswer1());
        binding.answer2.setText(test.getAnswer2());
        binding.answer3.setText(test.getAnswer3());
        binding.answer4.setText(test.getAnswer4());
    }

    private void loadTest() {
        list1 = new ArrayList<>();
        list1.add(new TestClass("Quyidagi qaysi manzilgoh bronza davriga taalluqli?", "Selungur", "Jarqo`rg`on", "Ko`lbuloq", "Qo`shilish", "Jarqo`rg`on"));
        list1.add(new TestClass("552-yilda kim \"xoqon\" deb e`lon qilinadi?", "To`nyabg`u", "El Arslon", "Bumin", "Istami", "Bumin"));
        list1.add(new TestClass("Ilk o`rta asrlarda Balx shahri quyidagi qaysi davlatning poytaxti bo`lgan?", "Xiyoniylar", "Kidariylar", "Eftallar", "Turk", "Kidariylar"));
        list1.add(new TestClass("O`rta paleolit davri sanasi to`g`ri ko`rsatilgan javob variantini aniqlang.", "1 million - 100-ming yilliklar", "100 - 40-ming yilliklar", "40 - 12-ming yilliklar", "17 - 7-ming yilliklar", "100 - 40-ming yilliklar"));
        list1.add(new TestClass("Kir II va massagetlar malikasi To`maris o`rtasidagi jang haqida ma`lumot bergan tarixchini ko`rating.", "Gerodot", "Arrian", "Poliyen", "Strabon", "Gerodot"));
        list1.add(new TestClass("Quyidagi qaysi manzilgoh Surxondaryo viloyatida joylashgan?", "Selungur", "Jarqo`rg`on", "Ko`lbuloq", "Qo`shilish", "Jarqo`rg`on"));
        list1.add(new TestClass("Yunonistonda Zardusht ismining \"Zorastr\" shaklida talaffuz qilinishiga asosiy sabab nima?", "bu yerda uni birinchi galda donishmand yulduzshunos sifatida bilishar edi", "bu yerda uni birinchi galda faylasuf va ilohiyotshunos olim sifatida bilishar edi", "jamiyatdagi voqea-hodisalarni oldindan ko`ra biluvchi folbin sifatida bilishar edi", "uni Yunonistonda olim, shoir va ostadonlar yasovchi deb bilishar edi", "bu yerda uni birinchi galda donishmand yulduzshunos sifatida bilishar edi"));
        list1.add(new TestClass("Quyidagi qaysi daryoning qadimgi nomi Oks deb nomlangan?", "Amudaryo", "Sirdaryo", "Zarafshon", "Norin", "Amudaryo"));
        list1.add(new TestClass("673-yilda qaysi xalifaning farmoni bilan Ubaydulloh ibn Ziyod Amudaryoni Kechib o`tib Buxoro muzofotiga bostirib kirgan?", "Abu Bakr", "Abul Abbos Saffoh", "Umar ibn Abdulaziz", "Muoviya I", "Muoviya I"));
        list1.add(new TestClass("Turk xoqonligida kimlar \"Yabg`u\" unvoniga ega bo`lish huquqini qo`lga kirita olgan?", "yirik sarkardalar", "katta yer egalari", "xoqonning qarindoshlari", "din peshvolari", "xoqonning qarindoshlari"));
        list1.add(new TestClass("X asrda qayerda O`rta Osiyodagi birinchi madrasa \"Farjak\" madrasasi qurilgan?", "Buxoroda", "Samarqandda", "Bag`dodda", "G`ijduvonda", "Buxoroda"));
        list1.add(new TestClass("Ilk o`rta asrlarda O`rta Osiyoda shaharning ichki qismi qanday nom bilan atalgan?", "Ark", "Rabot", "Shahriston", "Ko`shk", "Shahriston"));
        list1.add(new TestClass("\"Shu tuproqda tug`ilibmiz, shu tuproqda o`lamiz\" degan so`zlar kimga tegishli", "Jaloliddin Manguberdi", "Temur Malik", "Muhammad Narshaxiy", "Najmiddin Kubro", "Najmiddin Kubro"));
        list1.add(new TestClass("Qutayba boshchiligida arab qo`shiinlari Buxoroni egallagan yil qaysi?", "705-yil", "709-yil", "710-yil", "712-yil", "709-yil"));
        list1.add(new TestClass("Ulug` shayx, tasavvufning taniqli vakillaridan biri, kubroviylik tariqatining asoschisi Najmiddin Kubroning taxallusi qanday ma`noni beradi?", "\"dinning ulug` yulduzi\"", "\"yo`lchi yulduz\"", "\"musulmonlar e`tiqodini tuzatuvchi\"", "\"arablar va g`ayri arablar ustozi\"", "\"dinning ulug` yulduzi\""));

        list2 = new ArrayList<>();
        list2.add(new TestClass("Hisoblang:2017-2*(2021-2018)+2020", "4032", "4031", "4030", "0", "4031"));
        list2.add(new TestClass("Hisoblang:49*203+73-1935/45", "1091", "1119", "2812", "9977", "9977"));
        list2.add(new TestClass("Quyidagi sonlar orasida nechtasi 4 ga qoldiqsiz bo`linadi: \n a)65230500  b)334546  c)345672  d)789678", "2", "1", "3", "4", "2"));
        list2.add(new TestClass("500 va 480 sonlarining eng katta umumiy karralisi, eng kichik umumiy bo`luvchisidan necha marta katta?", "600", "612", "355", "462", "600"));
        list2.add(new TestClass("96 va 584 sonlarining umumiy bo`luvchilari nechta?", "3", "4", "5", "6", "4"));
        list2.add(new TestClass("0,4,5,7 raqamlari yordamida, ularni takrorlamasdan yozilgan eng katta to`rt xonali va eng kichik to`rt xonali sonlar ayirmasini toping.", "3483", "2236", "3326", "3356", "3483"));
        list2.add(new TestClass("Qaysi juftlik o`zaro tub sonlardan iborat?", "(19;152)", "(312;13)", "(102;81)", "51;56", "(19;152)"));
        list2.add(new TestClass("1 dan 62 gacha sonlar ketma-ket yozilganda uch raqami necha marta takrorlanadi?", "13", "14", "15", "16", "16"));
        list2.add(new TestClass("Qurilish mollari do`konida 3150 qop(gips) keltirildi. 1-kun 380 qop sotildi. 2-kun birinchi kundagidan 2 marta ko`p sotildi. 3-kun esa 2-kundagidan 230 taga kam sotildi. Do`konda necha qop (gips) qoldi?", "780", "1240", "2578", "1480", "1480"));
        list2.add(new TestClass("Uch yuz ikki ming beshni raqamlarda ifodalang.", "30205", "320005", "302005", "3205", "302005"));
        list2.add(new TestClass("888888/222 ni hisoblang.", "44", "404", "440", "4004", "4004"));
        list2.add(new TestClass("Hisoblang: 36*68+6225/249-2000", "256", "473", "231", "534", "473"));
        list2.add(new TestClass("19 dan 53 gacha nechta murakkab son bor?", "9", "26", "17", "33", "26"));
        list2.add(new TestClass("1;27;19;35;49 sonlar orasida nechta o`zaro tub sonlar jufti mavjud?", "10", "12", "9", "6", "9"));
        list2.add(new TestClass("1200 sonining natural bo`luvchilari sonini toping.", "18", "23", "30", "28", "30"));

        list3 = new ArrayList<>();
        list3.add(new TestClass("Qaysi grammatik ma`no maxsus ko`rsatkichga ega emas?", "bosh kelishik", "otlarda birlik", "bo`lishsizlik", "A,B", "A,B"));
        list3.add(new TestClass("Turlanish bilan bog`liq tovush o`zgarishi bo`ladigan so`zlar qatorini toping.", "kitob,xo`roz,ittifoq", "samo,osmon,shivir", "bo`taloq,chiroq,etik", "o`qish,pedagog,yetti", "bo`taloq,chiroq,etik"));
        list3.add(new TestClass("Quyidagi -cha qo`shimchasi bilan shakllangan so`zlarning qaysi qatorida kichraytirish ma`nosi ifodalanmagan?", "qizcha,ko`zacha,daftarcha", "qushcha,soatcha,zarracha", "yulduzcha,bog`cha,kitobcha", "novcha,yangicha,o`zgacha", "novcha,yangicha,o`zgacha"));
        list3.add(new TestClass("Ko`plik qo`shimchasini olmaydigan so`zlar qatorini belgilang.", "siz,kitob,qalam", "safar,oqshom,u,olim", "quvonch,men,sen", "kun,baliq,ishchi,kiyim", "quvonch,men,sen"));
        list3.add(new TestClass("Qaysi qatorda faqat birlikda qo`llanadigan otlar berilgan?", "ko`z,qosh,bosh", "uyqu,soat,ko`ngil", "un,yog`,suv", "barcha javoblarda", "barcha javoblarda"));
        list3.add(new TestClass("-lar qo`shimchasi taxmin ma`nosini anglatgan gapni aniqlang.", "Oyimlar ancha kech qaytdilar.", "Karimlar uylariga endi ketishdi.", "Oyoqlarim zirqirab og`riyapti.", "Qishloqqa ham o`n chaqirimlar bor.", "Qishloqqa ham o`n chaqirimlar bor."));
        list3.add(new TestClass("Qaysi qatorda faqat otlar berilgan?", "uchqun,to`lqin,bilimdon,qalamdon", "yong`in,toshqin,tuzdon,kuldon", "tuyg`un,keskin,qumdon,xumdon", "so`lg`in,turg`un,suvdon", "yong`in,toshqin,tuzdon,kuldon"));
        list3.add(new TestClass("Quyidagi gapda qaysi kelishiklar bilan bog`liq xato mavjud? Uyg`ur xati o`z tarixi mobaynida uyg`urlarnigina emas, balki butun turkiy xalqlarning umumiy yozuviga aylandi.", "bosh kelishik, tushum kelishigi", "qaratqich kelishigi, tushum kelishigi", "tushum kelishigi, jo`nalish kelishigi", "jo`nalish kelishigi, o`rin-payt kelishigi", "qaratqich kelishigi, tushum kelishigi"));
        list3.add(new TestClass("-lar qo`shimchasi qaysi gapda kesatiq, kinoya ma`nosini ifodalagan?", "U qanday azoblarni boshidan kechirmaydi.", "Og`a sog` borsangiz, avval dadamlarga salom ayting(Hamza).", "Bu yerda bir paytlar xaroba uyar bor edi.", "O`zlariyam qadam ranjida qilibdilar-da.", "O`zlariyam qadam ranjida qilibdilar-da."));
        list3.add(new TestClass("Qaysi qatordagi so`zlardan -q,-k qo`shimchalari bilan ot yasalganda o`zakda tovush o`zgarishi bo`ladi?", "o`ta,ista", "kura,yama", "bo`ya,sana", "ela,tara", "bo`ya,sana"));
        list3.add(new TestClass("Berilgan so`zlardan qaysi biri fonetik o`zgarish asosida yozilgan?", "ulg`aymoq", "ishda", "stol", "ketdi", "ulg`aymoq"));
        list3.add(new TestClass("Shundan so`ng ikkovi shunchalik qalinlashdiki, hatto bir juma kuni Murodxo`ja domlaning mehmonxonasida bir haftalik so`z boyligini aytib tamom qildi.  Ushbu gapda nechta so`z fonetik o`zgarish asosida yozilgan?", "2", "3", "4", "5", "5"));
        list3.add(new TestClass("Axloqiy fazilatlaring yuqori estetik diding bilan uyg`unlashgandagina husningga husn qo`shiladi. Ushbu gapda nechta chuqur til orqa undoshlari soni nechta?", "4 ta", "6 ta", "7 ta", "5 ta", "5 ta"));
        list3.add(new TestClass("Faqat jarangsiz undoshlardan tashkil topoga so`zni toping.", "taraqqiyot", "tafakkur", "shovqin", "huquq", "huquq"));
        list3.add(new TestClass("Undoshlar qaysi xususiyatiga ko`ra til,lab va bo`g`iz undoshlariga bo`linadi?", "hosil bo`lish o`rniga ko`ra", "hosil bo`lish usuliga ko`ra", "ovoz va shovqinning ishtirokiga ko`ra", "un paychalarining ishtirokiga ko`ra", "hosil bo`lish o`rniga ko`ra"));
    }
}