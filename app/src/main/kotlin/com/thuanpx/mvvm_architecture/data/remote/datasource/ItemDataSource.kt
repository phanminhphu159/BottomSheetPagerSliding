package com.thuanpx.mvvm_architecture.data.remote.datasource

import androidx.lifecycle.MutableLiveData
import com.thuanpx.mvvm_architecture.base.BaseDataSource
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.model.entity.Item
import com.thuanpx.mvvm_architecture.model.response.BaseResponse
import com.thuanpx.mvvm_architecture.utils.coroutines.ApiResponse

/**
 * Created by ThuanPx on 4/3/22.
 */
class ItemDataSource(
    private val apiService: ApiService,
    isLoading: MutableLiveData<Boolean>
) : BaseDataSource<Item>() {

    override val loading: MutableLiveData<Boolean> = isLoading

    override suspend fun requestMore(nextPage: Int): ApiResponse<BaseResponse<List<Item>>> {
        return apiService.fetchItems(page = nextPage * 6)
    }
}
