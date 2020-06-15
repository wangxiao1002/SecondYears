<template>
  <div class="color-list">
    <div></div>
  </div>
</template>
<script>
export default {
  mounted() {
    this.$dragging.$on("dragged", ({ value }) => {
      console.log(value.item);
      console.log(value.list);
      console.log(value.otherData);
    });
    this.$dragging.$on("dragend", () => {});
  },

  data() {
    return {
    };
  }
};
</script>
