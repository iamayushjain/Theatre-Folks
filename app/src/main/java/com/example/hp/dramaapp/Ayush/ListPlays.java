package com.example.hp.dramaapp.Ayush;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.dramaapp.MainActivity;
import com.example.hp.dramaapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by PASSION IS LIFE on 19-10-2016.
 */

public class ListPlays extends Activity {
    String[] playsName = {"Hamlet","Julius Caesar",  "As you like it"};
    ListView listPlays, listActors;
    String[] attributes = {"Tall", "Thin", "Fair", "Male", "Comedy", "Young"};
    ArrayList<String> favorable, allPossible, dialoguesList;
    TextView allListPossible;
    String selectedPlayName,selectedPlayString;
    float actorScore;
    String actorDescription;
    Integer[] web={R.drawable.hamlet,R.drawable.juliusceaser,R.drawable.asyoulikeit};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_plays);
        init();

    }


    void JsonAnalysis(String responce) {
        String data = "";
        try {
            JSONObject jsonRootObject = new JSONObject(responce);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("Employee");

            //Iterate the jsonArray and print the info of JSONObjects
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSONObject jsonObject = jsonArray.getString(i);

                String name = jsonArray.getJSONObject(i).optString("Name").toString();
                String p1 = jsonArray.getJSONObject(i).optString("P1").toString();
                String p2 = jsonArray.getJSONObject(i).optString("P2").toString();
                String p3 = jsonArray.getJSONObject(i).optString("P3").toString();
                String p4 = jsonArray.getJSONObject(i).optString("P4").toString();
                String p5 = jsonArray.getJSONObject(i).optString("P5").toString();
                String p6 = jsonArray.getJSONObject(i).optString("P6").toString();
                allPossible.add(name);
                float[] weights = new float[6];
                if (p1.equalsIgnoreCase("Tall")) {
                    weights[0] = 1.60f;
                    weights[1] = .4f;
                    weights[2] = .7f;
                    weights[3] = 1.5f;
                    weights[4] = .7f;
                    weights[5] = 1.1f;
                } else if (p1.equalsIgnoreCase("Short")) {
                    weights[0] = 1.60f;
                    weights[1] = .8f;
                    weights[2] = .7f;
                    weights[3] = 1.3f;
                    weights[4] = .7f;
                    weights[5] = 0.9f;

                } else {
                    weights[0] = 1.60f;
                    weights[1] = .6f;
                    weights[2] = .7f;
                    weights[3] = 1.3f;
                    weights[4] = .7f;
                    weights[5] = 1.1f;

                }
                float sum = 0;
                if (p1.equalsIgnoreCase(attributes[0])) {
                    sum += weights[0];
                }

                if (p2.equalsIgnoreCase(attributes[1])) {
                    sum += weights[1];
                }

                if (p3.equalsIgnoreCase(attributes[2])) {
                    sum += weights[2];
                }
                if (p4.equalsIgnoreCase(attributes[3])) {
                    sum += weights[3];
                }
                if (p5.equalsIgnoreCase(attributes[4])) {
                    sum += weights[4];
                }

                if (p6.equalsIgnoreCase(attributes[5])) {
                    sum += weights[5];
                }
                System.out.println(sum);
                if (sum >= 3.0f) {
                    favorable.add(name);
                }
                //String na = jsonArray.getJSONObject(i).optString("Name").toString();

                System.out.println(name);

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        // controlVariable=0;
    }

    void JsonAnalysisDialogue(String play, String responce) {
        String data = "";
        try {
            JSONObject jsonRootObject = new JSONObject(play);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("Employee");

            //Iterate the jsonArray and print the info of JSONObjects
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSONObject jsonObject = jsonArray.getString(i);

                String name = jsonArray.getJSONObject(i).optString("Name").toString();
                if (name.equalsIgnoreCase(responce)) {
                    dialoguesList.add(jsonArray.getJSONObject(i).optString("Dialog1").toString());
                    dialoguesList.add(jsonArray.getJSONObject(i).optString("Dialog2").toString());
                    dialoguesList.add(jsonArray.getJSONObject(i).optString("Dialog3").toString());
                    dialoguesList.add(jsonArray.getJSONObject(i).optString("Dialog4").toString());
                    dialoguesList.add(jsonArray.getJSONObject(i).optString("Dialog5").toString());
                    dialoguesList.add(jsonArray.getJSONObject(i).optString("Dialog6").toString());
                    dialoguesList.add(jsonArray.getJSONObject(i).optString("Dialog7").toString());
                    dialoguesList.add(jsonArray.getJSONObject(i).optString("Dialog8").toString());
                    actorDescription = jsonArray.getJSONObject(i).optString("Intro").toString();
                    String p1 = jsonArray.getJSONObject(i).optString("P1").toString();
                    String p2 = jsonArray.getJSONObject(i).optString("P2").toString();
                    String p3 = jsonArray.getJSONObject(i).optString("P3").toString();
                    String p4 = jsonArray.getJSONObject(i).optString("P4").toString();
                    String p5 = jsonArray.getJSONObject(i).optString("P5").toString();
                    String p6 = jsonArray.getJSONObject(i).optString("P6").toString();
                    float[] weights = new float[6];
                    if (p1.equalsIgnoreCase("Tall")) {
                        weights[0] = 1.60f;
                        weights[1] = .4f;
                        weights[2] = .7f;
                        weights[3] = 1.5f;
                        weights[4] = .7f;
                        weights[5] = 1.1f;
                    } else if (p1.equalsIgnoreCase("Short")) {
                        weights[0] = 1.60f;
                        weights[1] = .8f;
                        weights[2] = .7f;
                        weights[3] = 1.3f;
                        weights[4] = .7f;
                        weights[5] = 0.9f;

                    } else {
                        weights[0] = 1.60f;
                        weights[1] = .6f;
                        weights[2] = .7f;
                        weights[3] = 1.3f;
                        weights[4] = .7f;
                        weights[5] = 1.1f;

                    }
                    float sum = 0;
                    if (p1.equalsIgnoreCase(attributes[0])) {
                        sum += weights[0];
                    }

                    if (p2.equalsIgnoreCase(attributes[1])) {
                        sum += weights[1];
                    }

                    if (p3.equalsIgnoreCase(attributes[2])) {
                        sum += weights[2];
                    }
                    if (p4.equalsIgnoreCase(attributes[3])) {
                        sum += weights[3];
                    }
                    if (p5.equalsIgnoreCase(attributes[4])) {
                        sum += weights[4];
                    }

                    if (p6.equalsIgnoreCase(attributes[5])) {
                        sum += weights[5];
                    }
                    System.out.println(sum);
                    actorScore = sum;
                }
                System.out.println(name);

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        // controlVariable=0;
    }

    void init() {
        listPlays = (ListView) findViewById(R.id.listView);
        listActors = (ListView) findViewById(R.id.listView1);
        allListPossible = (TextView) findViewById(R.id.allListView);

       try {
           SharedPreferences sp = getSharedPreferences("Choices", 0);
           String params = sp.getString("Parameters", "");
           attributes = params.split(",");
       }
        catch (Exception e)
        {
            Intent intent=new Intent(ListPlays.this, MainActivity.class);
            startActivity(intent);
        }
        ImageCustomPlays adapternew=new ImageCustomPlays(ListPlays.this,web,playsName);
        listPlays.setAdapter(adapternew);
        listPlays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allPossible.clear();
                favorable.clear();

                switch (position) {
                    case 0:
                        JsonAnalysis(hamlet);
                        selectedPlayName = "Hamlet";
                        selectedPlayString=hamlet;
                        break;
                    case 1:
                        JsonAnalysis(juliar);
                        selectedPlayName = "Julius";
                        selectedPlayString=juliar;
                        break;
                    case 2:
                        JsonAnalysis(asYouLikeIt);
                        selectedPlayName = "AsYouLikeIt";
                        selectedPlayString=asYouLikeIt;
                        break;

//                Intent intent = new Intent(ListPlays.this, RecorderActivity.class);
//                startActivity(intent);
                }
                listPlays.setVisibility(View.GONE);
                listActors.setVisibility(View.VISIBLE);
                allListPossible.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adapternew = new ArrayAdapter<String>(ListPlays.this, android.R.layout.simple_list_item_1, favorable.toArray(new String[favorable.size()]));
                listActors.setAdapter(adapternew);

            }
        });

        listActors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialoguesList.clear();
                JsonAnalysisDialogue(selectedPlayString, listActors.getItemAtPosition(position).toString());
                Intent intent = new Intent(ListPlays.this, OptionsClass.class);
                intent.putExtra("Actor", listActors.getItemAtPosition(position).toString());
                intent.putExtra("ProfilePercentage", ((float) (actorScore * 100) / 6) + "");
                intent.putExtra("Dialogues", dialoguesList);
                intent.putExtra("Intro", actorDescription);
                intent.putExtra("Play", selectedPlayName);

                startActivity(intent);

            }
        });
        allListPossible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allListPossible.setVisibility(View.GONE);
                ArrayAdapter<String> adapternew = new ArrayAdapter<String>(ListPlays.this, android.R.layout.simple_list_item_1, allPossible.toArray(new String[allPossible.size()]));
                listActors.setAdapter(adapternew);

            }
        });
        favorable = new ArrayList<String>();
        allPossible = new ArrayList<String>();
        dialoguesList = new ArrayList<String>();
    }

    @Override
    public void onBackPressed() {
        if (!(listActors.getVisibility() == View.GONE)) {
            listActors.setVisibility(View.GONE);
            allListPossible.setVisibility(View.GONE);
            listPlays.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();

    }


    String asYouLikeIt = " {\n" +
            "         \"Employee\":[ { \"Name\": \"Rosalind\", \"P1\": \"Short\", \"P2\": \"Average\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Flexible\", \"P6\": \"Young\", \"Intro\": \"The daughter of Duke Senior. Rosalind, considered one of Shakespeare�s most delightful heroines.\", \"Dialog1\": \"Dear Celia, I show more mirth than I am mistress of.\", \"Dialog2\": \"and would you yet I were merrier? Unless you could.\", \"Dialog3\": \"teach me to forget a banished father, you must not.\", \"Dialog4\": \"learn me how to remember any extraordinary pleasure.\", \"Dialog5\": \"Well, I will forget the condition of my estate.\", \"Dialog6\": \"And your experience makes you sad.\", \"Dialog7\": \"I had rather have a fool to make me merry than experience to make me sad and to travel for it too!\", \"Dialog8\": \"Nay, an you be so tardy, come no more in my sight.\" }, { \"Name\": \"Orlando\", \"P1\": \"Tall\", \"P2\": \"Fit\", \"P3\": \"Dark\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"The youngest son of Sir Rowland de Bois and younger brother of Oliver.\", \"Dialog1\": \"I attend them with all respect and duty.\", \"Dialog2\": \"No, fair princess; he is the general challenger.\", \"Dialog3\": \"I come but in, as others do, to try with him the strength of my youth.\", \"Dialog4\": \"Ready, sir; but his will hath in it a more modest working.\", \"Dialog5\": \"Yes, I beseech your grace: I am not yet well breathed.\", \"Dialog6\": \"I take some joy to say you are, because I would be talking of her.\", \"Dialog7\": \"Then in mine own person I die.\", \"Dialog8\": \"Then love me, Rosalind.\" }, { \"Name\": \"Duke_Senior\", \"P1\": \"Tall\", \"P2\": \"Fat\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Old\", \"Intro\": \"The father of Rosalind and the rightful ruler of the dukedom in which the play is set.\", \"Dialog1\": \"Now, my co-mates and brothers in exile.\", \"Dialog2\": \"Hath not old custom made this life more sweet.\", \"Dialog3\": \"Than that of painted pomp? Are not these woods.\", \"Dialog4\": \"More free from peril than the envious court?\", \"Dialog5\": \"Here feel we but the penalty of Adam.\", \"Dialog6\": \"O my dear niece, welcome thou art to me!\", \"Dialog7\": \"Even daughter, welcome, in no less degree.\", \"Dialog8\": \"To one his lands withheld, and to the other\" }, { \"Name\": \"Jaques\", \"P1\": \"Average\", \"P2\": \"Fit\", \"P3\": \"Average\", \"P4\": \"Male\", \"P5\": \"Flexible\", \"P6\": \"Young\", \"Intro\": \"A faithful lord who accompanies Duke Senior into exile in the Forest of Ardenne.\", \"Dialog1\": \"More, more, I prithee, more.\", \"Dialog2\": \"I thank it. More, I prithee, more. I can suck.\", \"Dialog3\": \"melancholy out of a song, as a weasel sucks eggs.\", \"Dialog4\": \"More, I prithee, more.\", \"Dialog5\": \"I do not desire you to please me; I do desire you to sing.\", \"Dialog6\": \"Come, more; another stanzo: call you 'em stanzos?\", \"Dialog7\": \"Nay, I care not for their names; they owe me nothing.\", \"Dialog8\": \"Will you sing?\" }, { \"Name\": \"Celia\", \"P1\": \"Short\", \"P2\": \"Thin\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Flexible\", \"P6\": \"Young\", \"Intro\": \"The daughter of Duke Frederick and Rosalind�s dearest friend.\", \"Dialog1\": \"Why should this a desert be?\", \"Dialog2\": \"For it is unpeopled? No.\", \"Dialog3\": \"Tongues I'll hang on every tree.\", \"Dialog4\": \"That shall civil sayings show.\", \"Dialog5\": \"Some, how brief the life of man.\", \"Dialog6\": \"I could have taught my love to take thy father for mine\", \"Dialog7\": \"so wouldst thou, if the truth of thy love to me were so righteously\", \"Dialog8\": \"tempered as mine is to thee.\" }, { \"Name\": \"Duke_Frederick\", \"P1\": \"Average\", \"P2\": \"Fat\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"The brother of Duke Senior and usurper of his throne.\", \"Dialog1\": \"Come on, since the youth will not be entreated, his own peril on his forwardness.\", \"Dialog2\": \"How now, daughter and cousin!\", \"Dialog3\": \"Are you crept hither.\", \"Dialog4\": \"You will take little delight in it, I can tell you.\", \"Dialog5\": \"there is such odds in the man to see the wrestling?\", \"Dialog6\": \"No more, no more.\", \"Dialog7\": \"How dost thou, Charles?\", \"Dialog8\": \"Bear him away. What is thy name, young man?\" }, { \"Name\": \"Touchstone\", \"P1\": \"Short\", \"P2\": \"Fat\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Comedy\", \"P6\": \"Young\", \"Intro\": \"A clown in Duke Frederick�s court who accompanies Rosalind and Celia in their flight to Ardenne.\", \"Dialog1\": \"I care not for my spirits, if my legs were not weary.\", \"Dialog2\": \"For my part, I had rather bear with you than bear you.\", \"Dialog3\": \"yet I should bear no cross if I did bear you.\", \"Dialog4\": \"for I think you have no money in your purse.\", \"Dialog5\": \"I was in a better place: but travellers must be content.\", \"Dialog6\": \"And I mine. I remember, when I was in love I broke my sword.\", \"Dialog7\": \"and I remember the\", \"Dialog8\": \"kissing of her batlet and the cow's dugs that her pretty chopt hands had milked\" }, { \"Name\": \"Oliver\", \"P1\": \"Average\", \"P2\": \"Fit\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Flexible\", \"P6\": \"Young\", \"Intro\": \"The oldest son of Sir Rowland de Bois and sole inheritor of the de Bois estate.\", \"Dialog1\": \"O that your highness knew my heart in this!\", \"Dialog2\": \"I never loved my brother in my life.\", \"Dialog3\": \"Is there none here to give the woman?\", \"Dialog4\": \"Truly, she must be given, or the marriage is not lawful.\", \"Dialog5\": \"all shall flout me out of my calling.\", \"Dialog6\": \"Good morrow, fair ones: pray you, if you know,\", \"Dialog7\": \"Where in the purlieus of this forest stands\", \"Dialog8\": \"A sheep-cote fenced about with olive trees?\" }, { \"Name\": \"Silvius\", \"P1\": \"Short\", \"P2\": \"Thin\", \"P3\": \"Dark\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"A young, suffering shepherd, who is desperately in love with the disdainful Phoebe.\", \"Dialog1\": \"Sweet Phebe, do not scorn me; do not, Phebe.\", \"Dialog2\": \"Say that you love me not, but say not so.\", \"Dialog3\": \"In bitterness. The common executioner.\", \"Dialog4\": \"Whose heart the accustom'd sight of death makes hard.\", \"Dialog5\": \"Falls not the axe upon the humbled neck.\", \"Dialog6\": \"I'll not fail, if I live.\", \"Dialog7\": \"Though to have her and death were both one thing.\", \"Dialog8\": \"O Corin, that thou knew'st how I do love her!\" }, { \"Name\": \"Phebe\", \"P1\": \"Average\", \"P2\": \"Thin\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Flexible\", \"P6\": \"Young\", \"Intro\": \"A young shepherdess, who disdains the affections of Silvius.\", \"Dialog1\": \"I would not be thy executioner.\", \"Dialog2\": \"I fly thee, for I would not injure thee.\", \"Dialog3\": \"Thou tell'st me there is murder in mine eye.\", \"Dialog4\": \"Tis pretty, sure, and very probable.\", \"Dialog5\": \"That eyes, that are the frail'st and softest things.\", \"Dialog6\": \"But till that time\", \"Dialog7\": \"Come not thou near me: and when that time comes,\", \"Dialog8\": \"Afflict me with thy mocks, pity me not;\" } ]}\"";

    String juliar = " {\n" +
            "         \"Employee\":[ { \"Name\": \"Brutus\", \"P1\": \"Tall\", \"P2\": \"Average\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"A supporter of the republic who believes strongly in a government guided by the votes of senators.\", \"Dialog1\": \"A soothsayer bids you beware the ides of March.\", \"Dialog2\": \"Not I.\", \"Dialog3\": \"No, Cassius, for the eye sees not itself.\", \"Dialog4\": \"Into what dangers would you lead me Cassius.\", \"Dialog5\": \"What means this shouting? I do fear, the people\", \"Dialog6\": \"Choose Caesar for their king.\", \"Dialog7\": \"Into what dangers would you lead me, Cassius,\", \"Dialog8\": \"That you would have me seek into myself\" }, { \"Name\": \"Julius_Caesar\", \"P1\": \"Tall\", \"P2\": \"Fit\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"A great Roman general and senator, recently returned to Rome in triumph after a successful military campaign.\", \"Dialog1\": \"Calpurnia!\", \"Dialog2\": \"Set on; and leave no ceremony out.\", \"Dialog3\": \"Ha! who calls?\", \"Dialog4\": \"Set him before me; let me see his face.\", \"Dialog5\": \"What say'st thou to me now? speak once again.\", \"Dialog6\": \"Decius, well urged: I think it is not meet,\", \"Dialog7\": \"Mark Antony, so well beloved of Caesar,\", \"Dialog8\": \"Should outlive Caesar: we shall find of him\" }, { \"Name\": \"Antony\", \"P1\": \"Average\", \"P2\": \"Fit\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Flexible\", \"P6\": \"Young\", \"Intro\": \"A friend of Caesar. Antony claims allegiance to Brutus and the conspirators after Caesar�s death in order to save his own life.\", \"Dialog1\": \"Caesar, my lord?\", \"Dialog2\": \"I shall remember:\", \"Dialog3\": \"When Caesar says 'do this,' it is perform'd.\", \"Dialog4\": \"Caesar?\", \"Dialog5\": \"So to most noble Caesar.\", \"Dialog6\": \"With the most noble blood of all this world.\", \"Dialog7\": \"I do beseech ye, if you bear me hard,\", \"Dialog8\": \"Now, whilst your purpled hands do reek and smoke,\" }, { \"Name\": \"Cassius\", \"P1\": \"Tall\", \"P2\": \"Average\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"A talented general and longtime acquaintance of Caesar.\", \"Dialog1\": \"What, urge you your petitions in the street?\", \"Dialog2\": \"Come to the Capitol.\", \"Dialog3\": \"What enterprise, Popilius?\", \"Dialog4\": \"He wish'd to-day our enterprise might thrive.\", \"Dialog5\": \"I fear our purpose is discovered.\", \"Dialog6\": \"Come to the Capitol.\", \"Dialog7\": \"What, urge you your petitions in the street?\", \"Dialog8\": \"I fear our purpose is discovered.\" }, { \"Name\": \"Octavius\", \"P1\": \"Average\", \"P2\": \"Fit\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"Caesar�s adopted son and appointed successor.\", \"Dialog1\": \"Your brother too must die; consent you, Lepidus?\", \"Dialog2\": \"Prick him down, Antony.\", \"Dialog3\": \"Or here, or at the Capitol.\", \"Dialog4\": \"So you thought him.\", \"Dialog5\": \"You may do your will.\", \"Dialog6\": \"Then, Brutus, I have much mistook your passion;\", \"Dialog7\": \"By means whereof this breast of mine hath buried\", \"Dialog8\": \"Thoughts of great value, worthy cogitations.\" }, { \"Name\": \"Casca\", \"P1\": \"Tall\", \"P2\": \"Average\", \"P3\": \"Dark\", \"P4\": \"Male\", \"P5\": \"Flexible\", \"P6\": \"Old\", \"Intro\": \"A public figure opposed to Caesar�s rise to power.\", \"Dialog1\": \"Peace, ho! Caesar speaks.\", \"Dialog2\": \"Bid every noise be still: peace yet again!\", \"Dialog3\": \"You pull'd me by the cloak; would you speak with me?\", \"Dialog4\": \"Why, you were with him, were you not?\", \"Dialog5\": \"Why, for that too.\", \"Dialog6\": \"Are not you moved, when all the sway of earth\", \"Dialog7\": \"Shakes like a thing unfirm? O Cicero,\", \"Dialog8\": \"I have seen tempests, when the scolding winds\" }, { \"Name\": \"Calpurnia\", \"P1\": \"Tall\", \"P2\": \"Fit\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"Caesar�s wife. Calpurnia invests great authority in omens and portents.\", \"Dialog1\": \"What mean you, Caesar? think you to walk forth?\", \"Dialog2\": \"You shall not stir out of your house to-day.\", \"Dialog3\": \"Caesar, I never stood on ceremonies.\", \"Dialog4\": \"Yet now they fright me. There is one within.\", \"Dialog5\": \"Besides the things that we have heard and seen.\", \"Dialog6\": \"Alas, my lord,\", \"Dialog7\": \"Your wisdom is consumed in confidence.\", \"Dialog8\": \"Do not go forth to-day: call it my fear\" }, { \"Name\": \"Portia\", \"P1\": \"Average\", \"P2\": \"Average\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"Brutus�s wife; the daughter of a noble Roman who took sides against Caesar.\", \"Dialog1\": \"I prithee, boy, run to the senate-house.\", \"Dialog2\": \"Stay not to answer me, but get thee gone.\", \"Dialog3\": \"Why dost thou stay?\", \"Dialog4\": \"I would have had thee there, and here again.\", \"Dialog5\": \"Ere I can tell thee what thou shouldst do there.\", \"Dialog6\": \"Is Brutus sick? and is it physical\", \"Dialog7\": \"To walk unbraced and suck up the humours\", \"Dialog8\": \"Of the dank morning? What, is Brutus sick,\" }, { \"Name\": \"Flavius\", \"P1\": \"Average\", \"P2\": \"Fit\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"A tribune (an official elected by the people to protect their rights).\", \"Dialog1\": \"Thou art a cobbler, art thou?\", \"Dialog2\": \"But wherefore art not in thy shop today?\", \"Dialog3\": \"Why dost thou lead these men about the streets?\", \"Dialog4\": \"Assemble all the poor men of your sort.\", \"Dialog5\": \"Draw them to Tiber banks, and weep your tears\", \"Dialog6\": \"Go, go, good countrymen, and, for this fault,\", \"Dialog7\": \"Assemble all the poor men of your sort;\", \"Dialog8\": \"Draw them to Tiber banks, and weep your tears\" }, { \"Name\": \"Cicero\", \"P1\": \"Tall\", \"P2\": \"Average\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"A Roman senator renowned for his oratorical skill.\", \"Dialog1\": \"Good even, Casca: brought you Caesar home?\", \"Dialog2\": \"Why are you breathless? and why stare you so?\", \"Dialog3\": \"Why, saw you any thing more wonderful?\", \"Dialog4\": \"Indeed, it is a strange-disposed time.\", \"Dialog5\": \"But men may construe things after their fashion.\", \"Dialog6\": \"Indeed, it is a strange-disposed time:\", \"Dialog7\": \"But men may construe things after their fashion,\", \"Dialog8\": \"Clean from the purpose of the things themselves.\" } ]}\"";

    String hamlet = " {\n" +
            "         \"Employee\":[ { \"Name\": \"Hamlet\", \"P1\": \"Tall\", \"P2\": \"Fit\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"The Prince of Denmark, the title character, and the protagonist.\", \"Dialog1\": \"Not so, my lord; I am too much i' the sun.\", \"Dialog2\": \"A little more than kin, and less than kind.\", \"Dialog3\": \"Ay, madam, it is common.\", \"Dialog4\": \"Nor customary suits of solemn black.\", \"Dialog5\": \"Nor windy suspiration of forced breath.\", \"Dialog6\": \"O God!\", \"Dialog7\": \"Murder!\", \"Dialog8\": \"Haste me to know't, that I, with wings as swift.\" }, { \"Name\": \"Claudius\", \"P1\": \"Average\", \"P2\": \"Fat\", \"P3\": \"Average\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Old\", \"Intro\": \"The King of Denmark, Hamlet�s uncle, and the play�s antagonist.\", \"Dialog1\": \"Be as ourself in Denmark. Madam, come.\", \"Dialog2\": \"This gentle and unforced accord of Hamlet.\", \"Dialog3\": \"Sits smiling to my heart: in grace whereof.\", \"Dialog4\": \"No jocund health that Denmark drinks to-day.\", \"Dialog5\": \"But the great cannon to the clouds shall tell.\", \"Dialog6\": \"Take thy fair hour, Laertes; time be thine,\", \"Dialog7\": \"And thy best graces spend it at thy will!\", \"Dialog8\": \"But now, my cousin Hamlet, and my son\" }, { \"Name\": \"Gertrude\", \"P1\": \"Average\", \"P2\": \"Thin\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"The Queen of Denmark, Hamlet�s mother, recently married to Claudius.\", \"Dialog1\": \"Good Hamlet, cast thy nighted colour off.\", \"Dialog2\": \"And let thine eye look like a friend on Denmark.\", \"Dialog3\": \"Do not for ever with thy vailed lids.\", \"Dialog4\": \"Seek for thy noble father in the dust.\", \"Dialog5\": \"Thou know'st 'tis common; all that lives must die.\", \"Dialog6\": \"Good gentlemen, he hath much talk'd of you;\", \"Dialog7\": \"And sure I am two men there are not living\", \"Dialog8\": \"To whom he more adheres. If it will please you\" }, { \"Name\": \"Polonius\", \"P1\": \"Tall\", \"P2\": \"Fat\", \"P3\": \"Average\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Old\", \"Intro\": \"The Lord Chamberlain of Claudius�s court, a pompous, conniving old man.\", \"Dialog1\": \"The wind sits in the shoulder of your sail.\", \"Dialog2\": \"And you are stay'd for. There; my blessing with thee!\", \"Dialog3\": \"And these few precepts in thy memory.\", \"Dialog4\": \"See thou character. Give thy thoughts no tongue.\", \"Dialog5\": \"Nor any unproportioned thought his act.\", \"Dialog6\": \"What majesty should be, what duty is,\", \"Dialog7\": \"Why day is day, night night, and time is time,\", \"Dialog8\": \"Were nothing but to waste night, day and time.\" }, { \"Name\": \"Horatio\", \"P1\": \"Tall\", \"P2\": \"Average\", \"P3\": \"Fair\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"Hamlet�s close friend, who studied with the prince at the university in Wittenberg.\", \"Dialog1\": \"Friends to this ground.\", \"Dialog2\": \"A piece of him.\", \"Dialog3\": \"Tush, tush, 'twill not appear.\", \"Dialog4\": \"Well, sit we down.\", \"Dialog5\": \"And let us hear Bernardo speak of this.\", \"Dialog6\": \"You might have rhymed.\", \"Dialog7\": \"Very well, my lord.\", \"Dialog8\": \"Didst perceive?\" }, { \"Name\": \"Ophelia\", \"P1\": \"Average\", \"P2\": \"Thin\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"Polonius�s daughter, a beautiful young woman with whom Hamlet has been in love.\", \"Dialog1\": \"No more but so?\", \"Dialog2\": \"I shall the effect of this good lesson keep.\", \"Dialog3\": \"As watchman to my heart. But, good my brother.\", \"Dialog4\": \"Do not, as some ungracious pastors do.\", \"Dialog5\": \"Show me the steep and thorny way to heaven.\", \"Dialog6\": \"How should I your true love know\", \"Dialog7\": \"From another one?\", \"Dialog8\": \"By his cockle hat and staff,\" }, { \"Name\": \"Laertes\", \"P1\": \"Average\", \"P2\": \"Thin\", \"P3\": \"Fair\", \"P4\": \"Female\", \"P5\": \"Serious\", \"P6\": \"Young\", \"Intro\": \"Polonius�s son and Ophelia�s brother, a young man who spends much of the play in France.\", \"Dialog1\": \"Most humbly do I take my leave, my lord.\", \"Dialog2\": \"Farewell, Ophelia; and remember well.\", \"Dialog3\": \"What I have said to you.\", \"Dialog4\": \"I pray you, give me leave.\", \"Dialog5\": \"That drop of blood that's calm proclaims me bastard.\", \"Dialog6\": \"Cries cuckold to my father, brands the harlot\", \"Dialog7\": \"Even here, between the chaste unsmirched brow\", \"Dialog8\": \"Of my true mother.\" }, { \"Name\": \"Fortinbras\", \"P1\": \"Average\", \"P2\": \"Fat\", \"P3\": \"Average\", \"P4\": \"Male\", \"P5\": \"Serious\", \"P6\": \"young\", \"Intro\": \"The young Prince of Norway, whose father the king (also named Fortinbras) was killed by Hamlet�s father (also named Hamlet).\", \"Dialog1\": \"Where is this sight?\", \"Dialog2\": \"This quarry cries on havoc. O proud death.\", \"Dialog3\": \"What feast is toward in thine eternal cell.\", \"Dialog4\": \"That thou so many princes at a shot.\", \"Dialog5\": \"So bloodily hast struck?\", \"Dialog6\": \"This quarry cries on havoc. O proud death,\", \"Dialog7\": \"What feast is toward in thine eternal cell,\", \"Dialog8\": \"That thou so many princes at a shot\" }, { \"Name\": \"The_Ghost\", \"P1\": \"Average\", \"P2\": \"Average\", \"P3\": \"Average\", \"P4\": \"Flexible\", \"P5\": \"Serious\", \"P6\": \"young\", \"Intro\": \"The specter of Hamlet�s recently deceased father.\", \"Dialog1\": \"Mark me.\", \"Dialog2\": \"My hour is almost come.\", \"Dialog3\": \"When I to sulphurous and tormenting flames.\", \"Dialog4\": \"Must render up myself.\", \"Dialog5\": \"Pity me not, but lend thy serious hearing.\", \"Dialog6\": \"My hour is almost come.\", \"Dialog7\": \"Mark me.\", \"Dialog8\": \"When I to sulphurous and tormenting flames.\" } ]}\"";

}
