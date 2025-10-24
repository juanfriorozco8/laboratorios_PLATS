package uvg.plats.rickandmorty.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uvg.plats.rickandmorty.data.local.dao.CharacterDao
import uvg.plats.rickandmorty.data.local.dao.LocationDao
import uvg.plats.rickandmorty.data.local.entity.CharacterEntity
import uvg.plats.rickandmorty.data.local.entity.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var INSTANCE: RickAndMortyDatabase? = null

        fun getDatabase(context: Context): RickAndMortyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RickAndMortyDatabase::class.java,
                    "rick_and_morty_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}