package com.example.contact.contact.data.local

import androidx.room.*
import com.example.contact.maincontactscreen.di.CONTACT_TABLE
import io.reactivex.Single

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(entity: ContactEntity): Single<Unit>

    @Query("SELECT * FROM $CONTACT_TABLE")
    fun read(): Single<List<ContactEntity>>

    @Query("SELECT * FROM $CONTACT_TABLE WHERE number=:number")
    fun readByNumber(number: String): Single<ContactEntity>

    @Update
    fun update(oldEntity: ContactEntity, newEntity: ContactEntity): Single<Unit>

    @Delete
    fun delete(entity: ContactEntity): Single<Unit>
}