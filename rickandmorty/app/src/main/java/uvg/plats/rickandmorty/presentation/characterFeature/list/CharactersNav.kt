package uvg.plats.rickandmorty.presentation.characterFeature.list

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uvg.plats.rickandmorty.data.CharacterDb
import uvg.plats.rickandmorty.presentation.AppRoutes
import uvg.plats.rickandmorty.presentation.model.CharacterUI

fun NavGraphBuilder.characterListRoute(
    onCharacterClick: (Int) -> Unit,
    onFinishApp: () -> Unit
) {
    composable<AppRoutes.Characters> {
        BackHandler { onFinishApp() }
        val characters: List<CharacterUI> = CharacterDb().getAllCharacters()
        CharactersScreen(
            characters = characters,
            onOpenDetail = onCharacterClick
        )
    }
}



