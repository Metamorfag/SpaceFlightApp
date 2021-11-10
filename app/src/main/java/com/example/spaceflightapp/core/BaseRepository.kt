package com.example.spaceflightapp.core

import io.realm.RealmObject

interface BaseRepository<E : Abstract.DataObject> { //todo fix cache
    suspend fun fetchData(): E
    suspend fun update(): E
    abstract class Base<T : RealmObject,
            C : Abstract.CloudObject,
            D : Abstract.DataObject,
            E : Abstract.DataObject>(
        private val cacheDataSource: CacheDataSource<D>,
        private val cloudMapper: Abstract.Mapper.Data<List<C>, List<D>>,
        private val cacheMapper: Abstract.Mapper.Data<List<T>, List<D>>
    ) : BaseRepository<E> {
        override suspend fun fetchData() = try {
            val cachedList = getCachedDataList()
            if (cachedList.isEmpty()) {
                val cloudList = fetchCloudData()
                val list = cloudMapper.map(cloudList)
                cacheDataSource.save(list)
                returnSuccess(list)
            } else {
                returnSuccess(cacheMapper.map(cachedList))
            }
        } catch (e: Exception) {
            returnFail(e)
        }

        //override suspend fun delete() = cacheDataSource.delete()

        override suspend fun update() = try {
            val cloudList = fetchCloudData()
            val list = cloudMapper.map(cloudList)
            cacheDataSource.update(list)
            returnSuccess(list)
        } catch (e: Exception) {
            returnFail(e)
        }

        protected abstract suspend fun fetchCloudData(): List<C>
        protected abstract fun getCachedDataList(): List<T>
        protected abstract fun returnSuccess(list: List<D>): E
        protected abstract fun returnFail(e: Exception): E
    }
}