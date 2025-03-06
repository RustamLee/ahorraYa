<template>
  <div v-if="discount" class="discount-card">

    <!-- Структура для маленьких экранов (три колонки) -->
    <div class="small-screen-layout">
      <div class="days-column">
        <div
            v-for="day in ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY']"
            :key="day"
            :class="{ active: discount && (discount.dayOfWeek === 'ALL' || discount.dayOfWeek === day) }"
        >
          {{ day.charAt(0) + day.slice(1).toLowerCase() }}
        </div>
      </div>

      <div class="labels-column">
        <div><i class="ri-store-2-line"></i>STORE</div>
        <div><i class="ri-bank-line"></i>BANK</div>
        <div><i class="ri-bank-card-line"></i>CARD</div>
        <div><i class="ri-percent-line"></i>DISCOUNT</div>
        <div><i class="ri-money-dollar-box-line"></i>LIMIT</div>
        <div><i class="ri-time-line"></i>PERIOD</div>
        <div><i class="ri-information-line"></i>DETAILS</div>
      </div>

      <div class="values-column">
        <div>{{ discount.shop.name }}</div>
        <div>{{ discount.bank.name }}</div>
        <div>{{ discount.cardType }}</div>
        <div>{{ discount.discount }}%</div>
        <div>{{ discount.discountLimit }}$</div>
        <div>{{ discount.limitType }}</div>
        <div class="details-text" :title="discount.details">{{ truncateText(discount.details) }}</div>
      </div>

      <div class="actions-row">
        <div class="edit-icon" @click="onDeleteClicked"><i class="ri-delete-bin-line"></i></div>
        <div class="edit-icon" @click="openModal"><i class="ri-edit-line"></i></div>
      </div>
    </div>

    <!-- Структура для больших экранов (используем исходную структуру) -->
    <div class="default-layout">
      <div class="header-row">
        <div><i class="ri-store-2-line"></i>STORE</div>
        <div><i class="ri-bank-line"></i>BANK</div>
        <div><i class="ri-bank-card-line"></i>CARD</div>
        <div><i class="ri-percent-line"></i>DISCOUNT</div>
        <div><i class="ri-money-dollar-box-line"></i>LIMIT</div>
        <div><i class="ri-time-line"></i>PERIOD</div>
        <div class="edit-icon" @click="openModal"><i class="ri-edit-line"></i></div>
      </div>

      <div class="data-row">
        <div>{{ discount.shop.name }}</div>
        <div>{{ discount.bank.name }}</div>
        <div>{{ discount.cardType }}</div>
        <div>{{ discount.discount }}%</div>
        <div>{{ discount.discountLimit }}$</div>
        <div>{{ discount.limitType }}</div>
        <div class="edit-icon" @click="onDeleteClicked"><i class="ri-delete-bin-line"></i></div>
      </div>

      <div class="days-row">
        <div
            v-for="day in ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY']"
            :key="day"
            :class="{ active: discount && (discount.dayOfWeek === 'ALL' || discount.dayOfWeek === day) }"
        >
          {{ day.charAt(0) + day.slice(1).toLowerCase() }}
        </div>
      </div>

      <div class="details-row">
        <div><i class="ri-information-line"></i>DETAILS:</div>
        <div class="details-text">{{ discount.details }}</div>
      </div>
    </div>

    <DiscountCardEdit
        v-show="showModal"
        :discount="discount"
        @close="closeModal"
        @discount-updated="handleDiscountUpdated"
    />
  </div>
</template>

<script setup>
import {defineProps, defineEmits, ref} from 'vue';

import DiscountCardEdit from "@/components/DiscountCardEdit.vue";
const showModal = ref(false);

const props = defineProps({
  discount: Object
});
const emit = defineEmits(['discount-deleted', 'discount-updated']);

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const handleDiscountUpdated = (updatedDiscount) => {
  emit('discount-updated', updatedDiscount);
};

const onDeleteClicked = () => {
  emit('discount-deleted', props.discount.id);
};
const truncateText = (text, maxLength = 100) => {
  if (!text) return '';
  return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
};
</script>
<style scoped>
i {
  padding: 2px;
  color: #3483fa;
}

/*  Общие стили (применяются всегда)  */
.discount-card {
  padding: 16px;
  background-color: #ffffff;
  border-radius: 8px;
  max-width: 1200px;
  height: fit-content;
}

/* Стили для больших экранов (по умолчанию) */

.header-row {
  display: contents;
  font-weight: normal;
}

.header-row > div {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  font-weight: bold;
}
.default-layout {
  display: grid;
  grid-template-columns: repeat(6, 1fr) auto;
  grid-template-rows: auto auto auto auto;
  gap: 10px;
}

.labels-column{
  display: flex;
  align-items: flex-start;
}

.data-row {
  display: contents;
}

.days-row {
  display: contents;
  padding-left: 10px;
}

.details-row {
  grid-column: span 7;
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 10px;
  align-items: center;
  font-weight: bold;
  padding-left: 10px;
}

.details-row > div {
  display: flex;
  align-items: center;
}

.edit-icon {
  grid-column: 7;
  justify-self: center;
  cursor: pointer;
  color: #007BFF;
  font-size: 20px;
}

.days-row > div {
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  height: fit-content;
  border-radius: 8px;
  padding: 8px;
  border: 2px solid #f2f2f2;
  color: #3483fa;
}

.days-row > .active {
  background-color: #3483fa;
  color: white;
  font-weight: bold;
  border: none;
}

.details-text {
  padding: 8px;
  border: 2px solid #f2f2f2;
  border-radius: 8px;
  font-weight: lighter;
}

/* Стили для маленьких экранов (три колонки) */

@media (max-width: 768px) {

  .details-text {
    padding: 1px;
    border: 2px solid #f2f2f2;
    border-radius: 5px;
    font-weight: lighter;
    white-space: nowrap; /* Запрещаем перенос текста */
    overflow: hidden; /* Скрываем текст, выходящий за границы */
    text-overflow: ellipsis; /* Добавляем троеточие */
    width: 100px; /* Задаем максимальную ширину (настрой по вкусу) */
    box-sizing: border-box; /*  Учитываем padding и border в общей ширине */
    height: 100%;
  }

  .values-column {
    grid-column: 3;
    grid-row: 1 / 8;
    display: flex;
    flex-direction: column;
    gap: 9px;
    align-items: flex-end;
  }

  .default-layout {
    display: none;
  }

  .small-screen-layout {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-template-rows: repeat(7, auto) auto;
    gap: 10px;
    padding: 8px;
    font-size: 14px;
  }

  .days-column {
    grid-column: 1;
    grid-row: 1 / 8;
    display: flex;
    flex-direction: column;
    gap: 5px;
    margin-right: 10px;
  }

  .labels-column {
    grid-column: 2;
    grid-row: 1 / 8;
    display: flex;
    flex-direction: column;
    gap: 7px;
  }

  .actions-row {
    grid-column: 1 / 4;
    grid-row: 8;
    display: flex;
    justify-content: center; /*  Выравниваем по центру  */
    gap: 20px; /*  Уменьшаем отступ между элементами  */
  }

  /*  Дублируем стили для активного дня недели, но применяем их к .days-column > .active  */
  .days-column > div {
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    height: fit-content;
    border-radius: 5px;
    border: 2px solid #f2f2f2;
    color: #3483fa;
  }

  .days-column > .active {
    background-color: #3483fa;
    color: white;
    font-weight: bold;
    border: none;
  }
}

/* Явно показываем структуру для больших экранов, когда экран больше 768px */
@media (min-width: 769px) {
  .default-layout {
    display: grid; /* Показываем структуру для больших экранов */
  }

  .small-screen-layout {
    display: none; /* Скрываем структуру для маленьких экранов */
  }
}
</style>

