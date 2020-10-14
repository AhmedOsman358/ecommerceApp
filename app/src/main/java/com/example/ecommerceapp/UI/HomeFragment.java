package com.example.ecommerceapp.UI;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.ecommerceapp.API.ProductsResponse;
import com.example.ecommerceapp.API.RetrofitFactory;
import com.example.ecommerceapp.API.WebService;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Room.InsertAsyncTask;
import com.example.ecommerceapp.Room.RoomFactory;
import com.example.ecommerceapp.adapter.ProductAdabter;
import com.example.ecommerceapp.modela.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

         private RecyclerView homeRv;
         private List<ProductModel>productList=new ArrayList<>();
         private ProductAdabter productAdabter;
         private WebService webService;
         private ProgressDialog dialog;




    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_home, container, false);
        homeRv=view.findViewById(R.id.home_rv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setUpProgresDialog();

        setUpRecyclerView();
        callProductsAPI();


    }

    private void setUpProgresDialog() {
        dialog=new ProgressDialog(requireContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void callProductsAPI() {

        webService = RetrofitFactory.getRetrofit().create(WebService.class);
        Call<ProductsResponse> getProducts=webService.getProducts();
        getProducts.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {

                dialog.dismiss();
                productList.clear();
                productList.addAll(response.body().getProductsList());
                productAdabter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(requireContext(),2);
        homeRv.setLayoutManager(layoutManager);
        homeRv.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(12),true));
        homeRv.setItemAnimator(new DefaultItemAnimator());


















        productAdabter=new ProductAdabter(productList, requireContext(), new ProductAdabter.OnProductClickListener() {
            @Override
            public void onProductClick(View view, int position) {
                ProductModel selectedModel = productList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Current_product", selectedModel);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_productDetailsFragment, bundle);

            }
        }, new ProductAdabter.onAddProductClickListener() {
            @Override
            public void onAddProductClick(View view, int position) {
                ProductModel productModel=productList.get(position);
                productModel.setQuantity(1);
                new InsertAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDAO()).execute(productModel);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_cartFragment);

            }
        });






        homeRv.setAdapter(productAdabter);
        productAdabter.notifyDataSetChanged();

    }













    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {

        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}