package br.gov.pa.muiraquita.sample.rh.seam;

public abstract class AbstractPaginatedSeam extends RhAbstractSeam {

  // Paginacao
  protected Integer rowCount;
  protected Integer pageNumber;

  public AbstractPaginatedSeam() {
    super();
    this.rowCount = -1;
  }

  // TODO O generics não funcionou
  // abstract DataModelPaginator getDataModel(AbstractSispatDto buscas);

  public Integer getRowCount() {
    return rowCount;
  }

  public void setRowCount(Integer rowCount) {
    this.rowCount = rowCount;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

}
