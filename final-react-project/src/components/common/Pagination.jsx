import ReactPaginate from 'react-js-pagination';

const Pagination = ({ activePage, itemsCountPerPage, totalItemsCount, onPageChange }) => {
  return (
    <ReactPaginate
      activePage={activePage}
      itemsCountPerPage={itemsCountPerPage}
      totalItemsCount={totalItemsCount}
      pageRangeDisplayed={5}
      onChange={onPageChange}
      innerClass="pagination" // 원하는 클래스명으로 스타일링 가능
      itemClass="page-item" // 각 페이지 아이템에 대한 클래스명
      linkClass="page-link" // 링크에 대한 클래스명
    />
  );
};


export default Pagination;

