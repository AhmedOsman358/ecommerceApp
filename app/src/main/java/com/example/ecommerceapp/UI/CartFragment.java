package com.example.ecommerceapp.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Room.DeleteAsyncTask;
import com.example.ecommerceapp.Room.GetProductAsyncTask;
import com.example.ecommerceapp.Room.RoomFactory;
import com.example.ecommerceapp.Room.UpdateAsyncTask;
import com.example.ecommerceapp.adapter.CartAdapter;
import com.example.ecommerceapp.modela.ProductModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CartFragment extends Fragment {
    private RecyclerView CartRv;
    private CartAdapter cartAdapter;
    ArrayList<ProductModel> productList = new ArrayList<>();

    private Button clearBtn;
    private Button checkoutBtn;


    public interface OnIncClickListener {
        void onIncClick(View view, int position);
    }

    public interface OnDecClickListener {
        void onDecClick(View view, int position);
    }


    public CartFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_cart, container, false);

        CartRv = view.findViewById(R.id.cart_rv);
        clearBtn = view.findViewById(R.id.clear_btn);
        checkoutBtn = view.findViewById(R.id.checkout_btn);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecycleView();
        setUpClickListener();
        try {
            getAllProductsfromDB();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getAllProductsfromDB() throws ExecutionException, InterruptedException {
        productList.addAll(new GetProductAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDAO()).execute().get());
    }

    private void setUpClickListener() {
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDAO()).execute();
                productList.clear();
                cartAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpRecycleView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        CartRv.setLayoutManager(layoutManager);
        CartRv.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        cartAdapter = new CartAdapter(requireContext(), productList, new CartAdapter.OnIncClickListener() {
            @Override
            public void onIncClick(View view, int position) {
                productList.get(position).setQuantity(productList.get(position).getQuantity() + 1);
                new UpdateAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDAO()).execute(productList.get(position));
                cartAdapter.notifyDataSetChanged();
            }
        }, new CartAdapter.OnDecClickListener() {
            @Override
            public void onDecClick(View view, int position) {
                productList.get(position).setQuantity(productList.get(position).getQuantity() - 1);
                new UpdateAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDAO()).execute(productList.get(position));
                cartAdapter.notifyDataSetChanged();

            }
        });
        CartRv.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
    }


}