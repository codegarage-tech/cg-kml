package com.reversecoder.kml.activity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.reversecoder.library.customview.verticalstepper.VerticalStepperAdapter;
import com.reversecoder.library.customview.verticalstepper.VerticalStepperContentHolder;
import com.reversecoder.library.customview.verticalstepper.VerticalStepperLayout;
import com.reversecoder.kml.R;
import com.reversecoder.kml.guillotinemenu.ParentActivity;


public class RegistrationActivity extends ParentActivity {

    LinearLayout llHiddenConfirm;
    Button btnConfirm;
    private VerticalStepperLayout vStepperLayout;
    private VerticalStepperAdapter vStepperAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initRegistrationUI();
    }

    private final VerticalStepperContentHolder.ContentInteractionListener vStepperContentInteractionListener = new VerticalStepperContentHolder.ContentInteractionListener() {
        @Override
        public void onContinueClick(final VerticalStepperContentHolder stepContent) {
            if (stepContent.getStepIndex() == 0) {
//                if (AllSettingsManager.isNullOrEmpty(houseNoOrName)) {
//                    Toast.makeText(mContext, "You didn't input any house number/name.", Toast.LENGTH_SHORT).show();
//                }
                vStepperAdapter.finishStep(stepContent);
//                AllSettingsManager.forceHideSoftKeyboard(VerticalStepperCallATaxiActivity.this, edtHouseInfo);
            } else if (stepContent.getStepIndex() == 1) {
                if (vStepperAdapter.isPreviousStepsCompleted(stepContent.getStepIndex())) {
                    vStepperAdapter.finishStep(stepContent);
                } else {
                    Toast.makeText(getParentContext(), "Please complete previous steps.", Toast.LENGTH_SHORT).show();
                }
            } else if (stepContent.getStepIndex() == 2) {
                if (vStepperAdapter.isPreviousStepsCompleted(stepContent.getStepIndex())) {

//                    if (!NetworkManager.isConnected(mContext)) {
//                        Toast.makeText(mContext, getResources().getString(R.string.alert_network_error), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    vStepperAdapter.finishStep(stepContent);
                } else {
                    Toast.makeText(getParentContext(), "Please complete previous steps.", Toast.LENGTH_SHORT).show();
                }
            } else if (stepContent.getStepIndex() == 3) {
                if (vStepperAdapter.isPreviousStepsCompleted(stepContent.getStepIndex())) {
                    vStepperAdapter.finishStep(stepContent);
                } else {
                    Toast.makeText(getParentContext(), "Please complete previous steps.", Toast.LENGTH_SHORT).show();
                }
            }

            //check all steps are complete or not
            if (vStepperAdapter.isAllStepsCompleted()) {
                llHiddenConfirm.setVisibility(View.VISIBLE);
            } else {
                llHiddenConfirm.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onCancelClick(VerticalStepperContentHolder stepContent) {
            Log.d("onCancelClick", "step number is: " + stepContent.getStepIndex());
            if (stepContent.getStepIndex() == 0) {
                finish();
            } else {
                vStepperAdapter.cancelStep(stepContent);
            }
        }

        @Override
        public void onStepSelected(VerticalStepperContentHolder stepContent) {
            vStepperAdapter.jumpToStep(stepContent);

            //check all steps are complete or not
            if (vStepperAdapter.isAllStepsCompleted()) {
                llHiddenConfirm.setVisibility(View.VISIBLE);
            } else {
                llHiddenConfirm.setVisibility(View.INVISIBLE);
            }
        }
    };

    private void initRegistrationUI() {
        mContext = RegistrationActivity.this;

        llHiddenConfirm = (LinearLayout) findViewById(R.id.ll_hidden_confirm);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        vStepperLayout = (VerticalStepperLayout) findViewById(R.id.stepper_layout);
        vStepperAdapter = new VerticalStepperAdapter(VerticalStepperAdapter.ARABIC_NUMERAL_GENERATOR);
        vStepperLayout.setAdapter(vStepperAdapter);

        VerticalStepperContentHolder[] holders = new VerticalStepperContentHolder[4];
        for (int i = 0; i < holders.length; i++) {
            if (i == 0) {
                holders[i] = new VerticalStepperContentHolder("Full name", vStepperContentInteractionListener) {
                    @Override
                    public View inflateNewContentView(ViewGroup parent, View replacementView) {
                        View view = (View) LayoutInflater.from(getParentContext()).inflate(R.layout.vertical_stepper_item_full_name, null);

                        return view;
                    }
                };
            } else if (i == 1) {
                holders[i] = new VerticalStepperContentHolder("Email", vStepperContentInteractionListener) {
                    @Override
                    public View inflateNewContentView(ViewGroup parent, View replacementView) {
                        View view = (View) LayoutInflater.from(getParentContext()).inflate(R.layout.vertical_stepper_item_email, null);
                        return view;
                    }
                };
            } else if (i == 2) {
                holders[i] = new VerticalStepperContentHolder("Mobile number", vStepperContentInteractionListener) {
                    @Override
                    public View inflateNewContentView(ViewGroup parent, View replacementView) {
                        View view = (View) LayoutInflater.from(getParentContext()).inflate(R.layout.vertical_stepper_item_mobile_number, null);
                        return view;
                    }
                };
            } else if (i == 3) {
                holders[i] = new VerticalStepperContentHolder("Stay connected with facebook", vStepperContentInteractionListener) {
                    @Override
                    public View inflateNewContentView(ViewGroup parent, View replacementView) {
                        View view = (View) LayoutInflater.from(getParentContext()).inflate(R.layout.vertical_stepper_item_facebook, null);
                        return view;
                    }
                };
            }

        }

        vStepperAdapter.addSteps(holders);
    }


}
