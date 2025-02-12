<template>
  <header>
    <div class="header-content">
      <div class="logo-container">
        <h1 class="app-name">AhorraYa</h1>
        <p class="slogan">Save money with us</p>
      </div>

      <!-- Меню и иконка пользователя -->
      <div class="right-section">
        <!-- Иконка пользователя и имя -->
        <div class="user-info">
          <i class="ri-user-line user-icon"></i>
          <span class="user-name">User</span>
        </div>
        <!-- Меню -->
        <nav class="nav">
          <ul>
            <li><a href="#">Home</a></li>
            <li><a href="#">Discounts</a></li>
            <li><a href="#">Contact</a></li>
          </ul>
        </nav>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: 'HeaderApp',
}
</script>

<style>
/* Стили для хедера */

header{
  background-color: #ffe602;
  width: 100%; /* Фон на всю ширину экрана */
  display: flex;
  justify-content: center;
}

.header-content {
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  width: 100%;
  box-sizing: border-box;
  height: 100px;
  display: flex;
  max-width: 1200px;
}

/* Контейнер для логотипа и лозунга */
.logo-container {
  text-align: left;
  justify-content: flex-start;
}

.app-name {
  font-size: 2rem; /* увеличиваем размер шрифта для названия */
  margin: 0;
}

.slogan {
  font-size: 14px;
  margin-top: 5px;
  font-weight: normal;
  align-self: flex-end; /* прижимаем слоган к нижнему краю */
}

/* Правая часть хедера (меню и иконка пользователя) */
.right-section {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
  height: 100%; /* Для того чтобы пункты меню прижались к низу */
}

/* Иконка пользователя и имя */
.user-info {
  display: flex;
  align-items: center;
}

.user-icon {
  border-radius: 50%; /* делаем круглый вид для иконки */
  margin-right: 10px;
}

.user-name {
  font-size: 1rem;
}

/* Меню */
.nav ul {
  list-style-type: none; /* убираем маркеры списка */
  margin: 0;
  padding: 0;
  display: flex; /* Меню в строку */
  justify-content: flex-end; /* Выравниваем пункты меню по правому краю */
  align-items: flex-end; /* Прижимаем меню к нижнему краю */
  height: 100%;
  color: #4e4e4e;
}

.nav li {
  margin-left: 20px;
  color: #4e4e4e;/* отступы между пунктами меню */
}



.nav a {
  text-decoration: none; /* убираем подчеркивание */
  font-size: 14px;
  font-weight: normal;
  color: #4e4e4e;
}
</style>
