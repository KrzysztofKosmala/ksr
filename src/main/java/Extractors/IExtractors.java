package Extractors;


import DataLayer.Article;
import Extractors.Helpers.CalculationsForExtractors;

public interface IExtractors
{
    public void extract(Article article, CalculationsForExtractors calculationsForExtractors);
}
