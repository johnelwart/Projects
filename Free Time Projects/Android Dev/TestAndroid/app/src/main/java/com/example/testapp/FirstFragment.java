package com.example.testapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.testapp.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    
    private FragmentFirstBinding binding;
    CharSequence prevInput;
    Double argumentOne = 0.0;
    Double argumentTwo = 0.0;
    Double result = 0.0;
    int operation;
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
        
    }
    
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                        prevInput = binding.textView.getText();
                        
                        String input;
                        input = prevInput.toString();
                        input = input + "1";
                        
                        binding.textView.setText(input);
            }
        });
    
        binding.twobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + "2";
            
                binding.textView.setText(input);
            }
        });
    
        binding.threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + "3";
            
                binding.textView.setText(input);
            }
        });
    
        binding.fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + "4";
            
                binding.textView.setText(input);
            }
        });
    
        binding.fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + "5";
            
                binding.textView.setText(input);
            }
        });
    
        binding.sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + "6";
            
                binding.textView.setText(input);
            }
        });
    
        binding.sevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + "7";
            
                binding.textView.setText(input);
            }
        });
    
        binding.eightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + "8";
            
                binding.textView.setText(input);
            }
        });
        
        binding.nineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + "9";
            
                binding.textView.setText(input);
            }
        });
    
        binding.decimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                prevInput = binding.textView.getText();
            
                String input;
                input = prevInput.toString();
                input = input + ".";
            
                binding.textView.setText(input);
            }
        });
    
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                if (argumentOne == 0) {
                    argumentOne = Double.parseDouble(binding.textView.getText().toString());
                    
                    operation = 1;
                    binding.textView.setText("");
                    binding.textView3.setText(argumentOne + " +");
                }
            }
        });
    
        binding.minusButton.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                if (argumentOne == 0) {
                    argumentOne = Double.parseDouble(binding.textView.getText().toString());
                
                    operation = 2;
                    binding.textView.setText("");
                    binding.textView3.setText(argumentOne + " -");
                }
            }
        });
    
        binding.multiplyButton.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                if (argumentOne == 0) {
                    argumentOne = Double.parseDouble(binding.textView.getText().toString());
                
                    operation = 3;
                    binding.textView.setText("");
                    binding.textView3.setText(argumentOne + " *");
                }
            }
        });
    
        binding.divideButton.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                if (argumentOne == 0) {
                    argumentOne = Double.parseDouble(binding.textView.getText().toString());
                
                    operation = 4;
                    binding.textView.setText("");
                    binding.textView3.setText(argumentOne + " /");
                }
            }
        });
    
        binding.equalButton.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
                
                argumentTwo = Double.parseDouble(binding.textView.getText().toString());
                
                if (operation == 1) {
                    result = argumentOne + argumentTwo;
                } else if (operation == 2) {
                    result = argumentOne - argumentTwo;
                } else if (operation == 3) {
                    result = argumentOne * argumentTwo;
                } else if (operation == 4) {
                    result = argumentOne / argumentTwo;
                }
    
                argumentOne = 0.0;
                argumentTwo = 0.0;
    
                binding.textView.setText("");
                binding.textView3.setText(result.toString());
            }
        });
    
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this);
            
                argumentOne = 0.0;
                argumentTwo = 0.0;
                result = 0.0;
                operation = 0;
                
                binding.textView.setText("");
                binding.textView3.setText("");
            }
        });
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    
}