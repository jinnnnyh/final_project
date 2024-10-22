import ReactPaginate from 'react-js-pagination';

const Pagination = ({ currentPage, itemsCount, itemsPerPage, onPageChange }) => {
  return (
    <ReactPaginate
      activePage={currentPage}
      itemsCountPerPage={itemsPerPage}
      totalItemsCount={itemsCount}
      pageRangeDisplayed={5}
      onChange={onPageChange}
      innerClass="pagination"
      itemClass="page-item"
      linkClass="page-link"
    />
  );
};

export default Pagination;