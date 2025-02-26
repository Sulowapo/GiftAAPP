package com.itson.giftapp.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.itson.giftapp.view.FriendsActivity
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.itson.giftapp.adapter.CartAdapter
import com.itson.giftapp.data.CartDatabase
import com.itson.giftapp.databinding.FragmentCartBinding
import com.itson.giftapp.utils.SwipeHelper
import com.itson.giftapp.view.ProductDetailsActivity
import com.itson.giftapp.viewmodel.CartViewModel
import com.stripe.android.PaymentConfiguration
import kotlinx.coroutines.*


class CartFragment : Fragment() {
    private lateinit var viewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartDatabase: CartDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PaymentConfiguration.init(
            requireActivity(),
            "pk_test_qblFNYngBkEdjEZ16jxxoWSM"
        )
        // binding.recyclerViewCart.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]

        cartDatabase = CartDatabase.invoke(requireContext())
        viewModel.getDataFromRoom(requireContext(), binding, cartDatabase)
        /* if(viewModel.cartList.value?.isEmpty() == true){
             binding.emptyListMessage.visibility = View.VISIBLE
         }*/

        binding.progressBar.visibility = View.GONE


        CoroutineScope(Dispatchers.IO).launch {
            if (cartDatabase.cartDao().rowCount() == 0) {
                withContext(Dispatchers.Main) {
                    binding.emptyListMessage.visibility = View.VISIBLE
                }

            } else {
                withContext(Dispatchers.Main) {
                    binding.emptyListMessage.visibility = View.INVISIBLE
                }
            }
        }

        viewModelObserver()
        setUpRecyclerView()

        binding.buttonPurchase.setOnClickListener {
            checkOutProducts()
        }
    }

    private fun viewModelObserver() {
        viewModel.apply {
            calculateTotalAmount(cartDatabase)
            isCartListLoading.observe(viewLifecycleOwner, Observer {
            })
            totalAmounth.observe(viewLifecycleOwner, Observer {
                binding.totalAmount.text = it.toString()
                println("total amount inside observe : ${it}")
                println("text : ${binding.totalAmount.text.toString()} ")
            })
            isCartListEmpty.observe(viewLifecycleOwner, Observer {
                if (it) {
                    binding.emptyListMessage.visibility = View.VISIBLE
                } else {
                    binding.emptyListMessage.visibility = View.INVISIBLE
                }
            })
            product.observeForever(Observer {
                Log.i(TAG, "viewModelObserver: inside of observer")
                it.let {
                    Log.i(TAG, "viewModelObserver: $it")
                    val intent = Intent(context, ProductDetailsActivity::class.java)
                    intent.putExtra("product", it)
                    context?.startActivity(intent)
                }
            })

        }
    }

    private fun setUpRecyclerView() {

        cartAdapter = viewModel.cartList.value?.let {
            CartAdapter(it, requireContext(), cartDatabase)
        }!!
        binding.recyclerViewCart.adapter = cartAdapter
        binding.recyclerViewCart.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.recyclerViewCart) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val buttons = ArrayList<UnderlayButton>()
                val deleteButton = deleteButton(position)
                buttons.add(deleteButton)
                return buttons
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerViewCart)
    }

    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    viewModel.removeItemFromRoom(cartAdapter.getItemInfo(position)!!, cartDatabase)
                    cartAdapter.deleteItem(position)
                }
            })
    }

    fun checkOutProducts() {
        Log.d("CartFragment", "checkOutProducts() called")
        val intent = Intent(requireContext(), FriendsActivity::class.java)
        startActivity(intent)
    }
}