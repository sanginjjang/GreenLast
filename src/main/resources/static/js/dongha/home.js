// document.querySelectorAll('.category-item').forEach(item => {
//     item.addEventListener('click', function() {
//         const category = this.getAttribute('data-category');
//
//         const url = (category === '전체')
//             ? '/AllMain-api/class'
//             : `/AllMain-api/class?category=${encodeURIComponent(category)}`;
//
//         axios.get(url)
//             .then(response => {
//                 const classGrid = document.querySelector('.class-grid');
//                 classGrid.innerHTML = '';
//
//                 const classes = response.data;
//
//                 classes.forEach(classItem => {
//                     const classCard = `
//                             <a href="/home/detail/${classItem.classId}" class="class-card">
//                                 <img src="${classItem.fileUrl}" alt="강의 썸네일" />
//                                 <p>${classItem.classTitle}</p>
//                                 <p>${classItem.userId}</p>
//                                 <p>${Number(classItem.classPrice).toLocaleString()}원</p>
//                             </a>
//                         `;
//                     classGrid.insertAdjacentHTML('beforeend', classCard);
//                 });
//             })
//             .catch(error => {
//                 console.error('Error fetching classes:', error);
//             });
//     });
// });