package com.example.supperhero

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supperhero.model.Hero
import com.example.supperhero.data.HeroesRepository
import com.example.supperhero.ui.theme.SupperHeroTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroesList(
    heroes: List<Hero>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // đổi tên state nội bộ, giữ behavior
    val listVisibilityState = remember {
        MutableTransitionState(false).apply {
            targetState = true // bắt đầu animation ngay
        }
    }

    // tái sử dụng hằng cho spring params để code khác đi nhưng logic giống y xì
    val lowBounce = DampingRatioLowBouncy
    val veryLowStiffness = StiffnessVeryLow

    AnimatedVisibility(
        visibleState = listVisibilityState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = lowBounce)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(heroes) { position, itemHero ->
                // dùng tên biến khác, nhưng vẫn truyền đúng dữ liệu
                HeroListItem(
                    hero = itemHero,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = veryLowStiffness,
                                    dampingRatio = lowBounce
                                ),
                                // stagger theo vị trí (giữ nguyên công thức)
                                initialOffsetY = { offset -> offset * (position + 1) }
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun HeroListItem(
    hero: Hero,
    modifier: Modifier = Modifier
) {
    // tách vài val để cấu trúc khác đi
    val outerPadding = 16.dp
    val minItemHeight = 72.dp
    val avatarSize = 72.dp
    val avatarShape = RoundedCornerShape(8.dp)
    val textSpacing = 16.dp

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(outerPadding)
                .sizeIn(minHeight = minItemHeight)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(hero.nameRes),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(hero.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(Modifier.width(textSpacing))

            Box(
                modifier = Modifier
                    .size(avatarSize)
                    .clip(avatarShape)
            ) {
                Image(
                    painter = painterResource(hero.imageRes),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroPreview() {
    val previewHero = Hero(
        R.string.hero1,
        R.string.description1,
        R.drawable.android_superhero1
    )
    SupperHeroTheme {
        HeroListItem(hero = previewHero)
    }
}

@Preview("Heroes List")
@Composable
fun HeroesPreview() {
    SupperHeroTheme(darkTheme = false) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            // giữ nguyên truy cập datasource
            HeroesList(heroes = HeroesRepository.heroes)
        }
    }
}
