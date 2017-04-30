package com.app.swe443.openbankapp;


import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SignUpFrag4 extends android.support.v4.app.Fragment implements View.OnClickListener {

    private AutoCompleteTextView InitialValue;
    CheckBox checkBox;
    Button backButton3;
    Button continueButton3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up4, container, false);

        //Back & Continue buttons for third sign up page.
        backButton3 = (Button) view.findViewById(R.id.back_button4_4);
        continueButton3 = (Button) view.findViewById(R.id.continue_button4_4);
        backButton3.setOnClickListener(this);
        continueButton3.setOnClickListener(this);

        //Users agreement to terms.
        checkBox = (CheckBox) view.findViewById(R.id.checkBox_agree);

        //Terms info.
        //Lorem ipsum text for agreement.
        WebView terms = (WebView) view.findViewById(R.id.agreements);
        String text;
        text = "<html> <font color='white'> <body><p align=\"justify\">";
        text+= "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Duis venenatis velit a neque vulputate dignissim. Quisque at augue " +
                "ut ante egestas feugiat quis at magna. Donec at lacus vel ipsum ultrices" +
                "imperdiet. Donec quis semper nisi. Praesent facilisis dolor vel venenaf " +
                "condimentum. Phasellus id congue nisl. Praesent ut lacus risus. Aenean  " +
                "dui ut libero dapibus mattis. Aenean ultricies condimentum purus varius " +
                "interdum. Aenean dignissim dui at commodo rhoncus. Sed sit amet feugiat " +
                "quam, a interdum odio. Pellentesque ornare vehicula est, ac scelerisque " +
                "dolor dignissim maximus. Orci varius natoque penatibus et magnis dis " +
                "parturient montes, nascetur ridiculus mus. Nam ac ex molestie, interdum" +
                "nunc in, efficitur ante.\n" +
                "\n" +
                "Quisque dignissim consequat lobortis. Pellentesque scelerisque urna nec " +
                "mauris varius, sit amet luctus ex iaculis. Donec ullamcorper quis arcu " +
                "vitae euismod. Donec vel posuere massa, sit amet eleifend orci. Quisque " +
                "in metus sed leo sagittis venenatis. Aenean et orci at metus mattis " +
                "laoreet vel et ante. Nullam a nunc non ligula tempor dictum vitae sed " +
                "risus. Donec sapien arcu, ultricies fringilla arcu nec, egestas " +
                "fermentum leo.\n" +
                "\n" +
                "Aenean ullamcorper, enim quis tempus porta, sapien ex dapibus mauris, ac " +
                "tincidunt dolor sem aliquam sapien. Integer quam elit, iaculis quis rutrum" +
                "a, viverra a urna. Nunc eleifend augue quam, in maximus augue bibendum sed. " +
                "Duis gravida nisi sed blandit euismod. In hac habitasse platea dictumst. " +
                "Curabitur eget erat malesuada arcu malesuada semper. Praesent sagittis af" +
                "nisi ligula, sed blandit metus euismod a. Aliquam ut vulputate ipsum. Name " +
                "tempus non quam eu pretium. Fusce commodo orci in euismod pharetra. Ut " +
                "vitae finibus lectus. Fusce porta nulla mauris, ac venenatis nunc pharetra " +
                "in. Fusce malesuada enim enim, ac feugiat dui lobortis in. Nulla feugiat " +
                "sit amet quam in laoreet. If you don't fulfill the agreement then you have" +
                " to shave your hair";
        text+= "</p></body></html>";
        terms.loadData(text, "text/html", "utf-8");
        terms.setBackgroundColor(view.getSolidColor());
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_button4_4:
                getFragmentManager().popBackStack();
                break;

            case R.id.continue_button4_4:
                if (!checkBox.isChecked()) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Missing Field")
                            .setMessage("Please, agree to terms before continuing.")
                            .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    Intent intent = new Intent(getActivity(), HomeFrag.class);
//                    startActivity(intent);
                    fragmentTransaction.add(R.id.drawer_layout, new CreateAccountFrag(), "SignUpFrag4");
                    fragmentTransaction.hide(this);
                    fragmentTransaction.addToBackStack(this.getClass().getName());
                    fragmentTransaction.commit();
                }
                break;
        }
    }
}